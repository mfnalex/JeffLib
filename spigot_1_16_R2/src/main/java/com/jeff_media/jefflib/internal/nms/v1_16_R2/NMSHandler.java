package com.jeff_media.jefflib.internal.nms.v1_16_R2;

import com.jeff_media.jefflib.ItemStackUtils;
import com.jeff_media.jefflib.PacketUtils;
import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.PathNavigation;
import com.jeff_media.jefflib.ai.PathfinderGoal;
import com.jeff_media.jefflib.ai.TemptGoal;
import com.jeff_media.jefflib.data.*;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.nms.v1_16_R2.ai.CustomGoalExecutor;
import com.jeff_media.jefflib.internal.nms.v1_16_R2.ai.HatchedPathNavigation;
import com.jeff_media.jefflib.internal.nms.v1_16_R2.ai.HatchedTemptGoal;
import com.mojang.authlib.GameProfile;
import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.stream.Stream;

import static com.jeff_media.jefflib.internal.nms.v1_16_R2.NMS.*;

public class NMSHandler implements AbstractNMSHandler {

    private final MaterialHandler materialHandler = new MaterialHandler();
    private final BlockHandler blockHandler = new BlockHandler();

    @Override
    public AbstractNMSMaterialHandler getMaterialHandler() {
        return materialHandler;
    }

    @Override
    public AbstractNMSBlockHandler getBlockHandler() {
        return blockHandler;
    }

    @Override
    public void changeNMSEntityName(@Nonnull final Object entity, @Nonnull final String name) {
        ((Entity) entity).setCustomName(CraftChatMessage.fromString(name)[0]);
        for(final Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, new PacketPlayOutEntityMetadata(((Entity)entity).getId(),((Entity)entity).getDataWatcher(),true));
        }
    }

    @Override
    public Object createHologram(@Nonnull final Location location, @Nonnull final String line, @Nonnull final Hologram.Type type) {
        final CraftWorld craftWorld = (CraftWorld) location.getWorld();
        final World world = craftWorld.getHandle();
        final IChatBaseComponent baseComponent = CraftChatMessage.fromString(line)[0];
        final Entity entity;
        switch (type) {
            case EFFECTCLOUD:
                entity = new EntityAreaEffectCloud(world, location.getX(), location.getY(), location.getZ());
                final EntityAreaEffectCloud effectCloud = (EntityAreaEffectCloud) entity;
                effectCloud.setRadius(0);
                effectCloud.setWaitTime(0);
                effectCloud.setDuration(Integer.MAX_VALUE);
                break;
            case ARMORSTAND:
            default:
                entity = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());
                final EntityArmorStand armorStand = (EntityArmorStand) entity;
                armorStand.setNoGravity(true);
                armorStand.setInvisible(true);
                armorStand.setMarker(true);
                armorStand.setSmall(true);
        }

        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.setCustomName(baseComponent);
        entity.setCustomNameVisible(true);
        return entity;
    }

    @Override
    public void showEntityToPlayer(@Nonnull final Object entity, @Nonnull final Player player) {
        final PacketPlayOutSpawnEntity packetSpawn = new PacketPlayOutSpawnEntity((Entity) entity);
        PacketUtils.sendPacket(player, packetSpawn);

        final PacketPlayOutEntityMetadata packetMeta = new PacketPlayOutEntityMetadata(((Entity)entity).getId(), ((Entity)entity).getDataWatcher(), true);
        PacketUtils.sendPacket(player, packetMeta);
    }

    @Override
    public void hideEntityFromPlayer(@Nonnull final Object entity, @Nonnull final Player player) {
        final PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(((Entity) entity).getId());
        PacketUtils.sendPacket(player, packetDestroy);
    }

    @Override
    public void sendPacket(@Nonnull final Player player, @Nonnull final Object packet) {
        NMSPacketUtils.sendPacket(player, packet);
    }

    @Override
    public Pair<String, String> getBiomeName(@Nonnull final Location location) {
        return NMSBiomeUtils.getBiomeName(location);
    }

    @Override
    public void playTotemAnimation(@Nonnull final Player player) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Packet<PacketListenerPlayOut> packet = new PacketPlayOutEntityStatus(entityPlayer, (byte) 35);
        final PlayerConnection playerConnection = entityPlayer.playerConnection;
        playerConnection.sendPacket(packet);
    }

    @Override
    public void setHeadTexture(final Block block, @Nonnull final GameProfile gameProfile) {
        final World world = ((CraftWorld) block.getWorld()).getHandle();
        final BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        final TileEntitySkull skull = (TileEntitySkull) world.getTileEntity(blockPosition);
        skull.setGameProfile(gameProfile);
    }

    @Override
    public String itemStackToJson(@Nonnull final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        final NBTTagCompound compoundTag = new NBTTagCompound();
        nmsItemStack.save(compoundTag);
        return compoundTag.asString();
    }

    @Override
    public void setFullTimeWithoutTimeSkipEvent(@Nonnull final org.bukkit.World world, final long time, final boolean notifyPlayers) {
        final WorldServer level = ((CraftWorld)world).getHandle();
        level.setDayTime(time);
        if(notifyPlayers) {
            for (final Player player : world.getPlayers()) {
                final EntityPlayer serverPlayer = ((CraftPlayer) player).getHandle();
                if (serverPlayer.playerConnection != null) {
                    serverPlayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(serverPlayer.world.getTime(), serverPlayer.getPlayerTime(), serverPlayer.world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)));
                }
            }
        }
    }

    @Override
    public double[] getTps() {
        return ((CraftServer)Bukkit.getServer()).getHandle().getServer().recentTps;
    }

    @Override
    public int getItemStackSizeInBytes(org.bukkit.inventory.ItemStack itemStack) throws IOException {
        ByteCounter counter = new ByteCounter();
        NBTTagCompound tag = CraftItemStack.asNMSCopy(itemStack).getTag();
        if(tag == null) return ItemStackUtils.NO_DATA;
        tag.write(counter);
        return counter.getBytes();
    }

    @Override
    public String getDefaultWorldName() {
        return ( (CraftServer) Bukkit.getServer() ).getServer().propertyManager.getProperties().levelName;
    }

    @Override
    public TemptGoal createTemptGoal(org.bukkit.entity.LivingEntity entity, Stream<org.bukkit.Material> materials, double speed, boolean canScare) {
        final EntityCreature pmob = asPathfinder(entity);
        return new HatchedTemptGoal(entity, pmob,ingredient(materials), speed, canScare);
    }

    @Override
    public boolean addGoal(LivingEntity entity, PathfinderGoal goal, int priority) {
        final EntityCreature pmob = asPathfinder(entity);
        if(pmob == null) return false;
        if(goal instanceof net.minecraft.server.v1_16_R2.PathfinderGoal) {
            pmob.targetSelector.a(priority, (net.minecraft.server.v1_16_R2.PathfinderGoal) goal);
        } else if(goal instanceof CustomGoal) {
            pmob.targetSelector.a(priority, (net.minecraft.server.v1_16_R2.PathfinderGoal) ((CustomGoal)goal).getExecutor());
        } else {
            throw new UnsupportedOperationException("Unsupported goal type: " + goal.getClass().getName());
        }
        return false;
    }

    @Override
    public boolean moveTo(org.bukkit.entity.LivingEntity entity, double x, double y, double z, double speed) {
        final EntityCreature pmob = asPathfinder(entity);
        if(pmob == null) return false;
        return pmob.getNavigation().a(x, y, z, speed);
    }

    @Override
    public boolean isPathfinderMob(org.bukkit.entity.Entity entity) {
        return toNms(entity) instanceof EntityCreature;
    }

    @Override
    public boolean isServerRunnning() {
        return getDedicatedServer().isRunning();
    }

    @Override
    public com.jeff_media.jefflib.ai.CustomGoalExecutor getCustomGoalExecutor(CustomGoal customGoal, LivingEntity entity) {
        return new CustomGoalExecutor(customGoal, asPathfinderOrThrow(entity));
    }

    @Override
    public PathNavigation getPathNavigation(LivingEntity entity) {
        return new HatchedPathNavigation(NMS.asPathfinderOrThrow(entity).getNavigation());
    }

}
