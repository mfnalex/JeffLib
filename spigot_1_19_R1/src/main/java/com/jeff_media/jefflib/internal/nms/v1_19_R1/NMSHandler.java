package com.jeff_media.jefflib.internal.nms.v1_19_R1;

import com.jeff_media.jefflib.ItemStackUtils;
import com.jeff_media.jefflib.ai.*;
import com.jeff_media.jefflib.data.*;
import com.jeff_media.jefflib.internal.nms.v1_19_R1.ai.HatchedPathNavigation;
import com.jeff_media.jefflib.internal.nms.v1_19_R1.ai.HatchedTemptGoal;
import com.mojang.authlib.GameProfile;
import com.jeff_media.jefflib.PacketUtils;
import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.stream.Stream;

import static com.jeff_media.jefflib.internal.nms.v1_19_R1.NMS.*;

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
    public void showEntityToPlayer(@Nonnull final Object entity, @Nonnull final org.bukkit.entity.Player player) {
        PacketUtils.sendPacket(player, new ClientboundAddEntityPacket((Entity) entity));
        PacketUtils.sendPacket(player, new ClientboundSetEntityDataPacket(((Entity)entity).getId(), ((Entity)entity).getEntityData(), true));
    }

    @Override
    public void hideEntityFromPlayer(@Nonnull final Object entity, @Nonnull final org.bukkit.entity.Player player) {
        PacketUtils.sendPacket(player, new ClientboundRemoveEntitiesPacket(((Entity) entity).getId()));
    }

    @Override
    public void changeNMSEntityName(@Nonnull final Object entity, @Nonnull final String name) {
        ((Entity) entity).setCustomName(CraftChatMessage.fromString(name)[0]);
        for(final org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, new ClientboundSetEntityDataPacket(((Entity)entity).getId(),((Entity)entity).getEntityData(),true));
        }
    }

    @Override
    public Object createHologram(@Nonnull final Location location, @Nonnull final String line, @Nonnull final Hologram.Type type) {
        final CraftWorld craftWorld = (CraftWorld) location.getWorld();
        final ServerLevel world = craftWorld.getHandle();
        final Component baseComponent = CraftChatMessage.fromString(line)[0];
        final Entity entity;
        switch (type) {
            case EFFECTCLOUD:
                entity = new AreaEffectCloud(world, location.getX(), location.getY(), location.getZ());
                final AreaEffectCloud effectCloud = (AreaEffectCloud) entity;
                effectCloud.setRadius(0);
                effectCloud.setWaitTime(0);
                effectCloud.setDuration(Integer.MAX_VALUE);
                break;
            case ARMORSTAND:
            default:
                entity = new ArmorStand(world, location.getX(), location.getY(), location.getZ());
                final ArmorStand armorStand = (ArmorStand) entity;
                armorStand.setNoGravity(true); // setnogravity
                armorStand.setInvisible(true); // setinvisible
                armorStand.setMarker(true); // setmarker
                armorStand.setSmall(true); // setsmall
        }

        entity.setInvulnerable(true); // setinvulnerable
        entity.setSilent(true); // setsilent
        entity.setCustomName(baseComponent); // setcustomname
        entity.setCustomNameVisible(true); // setcustomnamevisible
        return entity;
    }

    @Override
    public void sendPacket(@Nonnull final org.bukkit.entity.Player player, @Nonnull final Object packet) {
        NMSPacketUtils.sendPacket(player, packet);
    }

    @Override
    public Pair<String, String> getBiomeName(@Nonnull final Location location) {
        return NMSBiomeUtils.getBiomeName(location);
    }

    @Override
    public void playTotemAnimation(@Nonnull final org.bukkit.entity.Player player) {
        final ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Packet<?> packet = new ClientboundEntityEventPacket(entityPlayer, (byte) 35);
        final Connection playerConnection = entityPlayer.connection.connection;
        playerConnection.send(packet);
    }

    @Override
    public void setHeadTexture(@Nonnull final Block block, @Nonnull final GameProfile gameProfile) {
        final ServerLevel world = ((CraftWorld) block.getWorld()).getHandle();
        final BlockPos blockPosition = new BlockPos(block.getX(), block.getY(), block.getZ());
        final SkullBlockEntity skull = (SkullBlockEntity) world.getBlockEntity(blockPosition,false);
        assert skull != null;
        skull.setOwner(gameProfile);
    }

    @Override
    public String itemStackToJson(@Nonnull final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        final CompoundTag compoundTag = new CompoundTag();
        nmsItemStack.save(compoundTag);
        return compoundTag.getAsString();
    }

    @Override
    public void setFullTimeWithoutTimeSkipEvent(@Nonnull final World world, final long time, final boolean notifyPlayers) {
        final ServerLevel level = ((CraftWorld)world).getHandle();
        level.setDayTime(time);
        if(notifyPlayers) {
            for (final Player player : world.getPlayers()) {
                final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
                if (serverPlayer.connection != null) {
                    serverPlayer.connection.send(new ClientboundSetTimePacket(serverPlayer.level.getGameTime(), serverPlayer.getPlayerTime(), serverPlayer.level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
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
        CompoundTag tag = CraftItemStack.asNMSCopy(itemStack).getTag();
        if(tag == null) return ItemStackUtils.NO_DATA;
        tag.write(counter);
        return counter.getBytes();
    }

    @Override
    public String getDefaultWorldName() {
        return ( (CraftServer) Bukkit.getServer() ).getServer().getProperties().levelName;
    }

    @Override
    public TemptGoal createTemptGoal(org.bukkit.entity.LivingEntity entity, Stream<Material> materials, double speed, boolean canScare) {
        final PathfinderMob pmob = asPathfinder(entity);
        return new HatchedTemptGoal(entity, pmob,ingredient(materials), speed, canScare);
    }

    @Override
    public boolean addGoal(LivingEntity entity, PathfinderGoal goal, int priority) {
        final PathfinderMob pmob = asPathfinder(entity);
        if(pmob == null) return false;
        if(goal instanceof Goal) {
            pmob.targetSelector.addGoal(priority, (Goal) goal);
        } else if(goal instanceof CustomGoal) {
            pmob.targetSelector.addGoal(priority, (Goal) ((CustomGoal)goal).getExecutor());
        } else {
            throw new UnsupportedOperationException("Unsupported goal type: " + goal.getClass().getName());
        }
        return false;
    }

    @Override
    public boolean moveTo(org.bukkit.entity.LivingEntity entity, double x, double y, double z, double speed) {
        final PathfinderMob pmob = asPathfinder(entity);
        if(pmob == null) {
            return false;
        }
        return pmob.getNavigation().moveTo(x, y, z, speed);
    }

    @Override
    public boolean isPathfinderMob(org.bukkit.entity.Entity entity) {
        return toNms(entity) instanceof PathfinderMob;
    }

    @Override
    public boolean isServerRunnning() {
        return getDedicatedServer().isRunning();
    }

    @Override
    public CustomGoalExecutor getCustomGoalExecutor(CustomGoal customGoal, LivingEntity entity) {
        return new com.jeff_media.jefflib.internal.nms.v1_19_R1.ai.CustomGoalExecutor(customGoal, asPathfinderOrThrow(entity));
    }

    @Override
    public PathNavigation getPathNavigation(org.bukkit.entity.LivingEntity entity) {
        return new HatchedPathNavigation(asPathfinderOrThrow(entity).getNavigation());
    }

}
