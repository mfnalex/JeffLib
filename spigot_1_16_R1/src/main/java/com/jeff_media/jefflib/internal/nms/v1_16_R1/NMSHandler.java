package com.jeff_media.jefflib.internal.nms.v1_16_R1;

import com.jeff_media.jefflib.ItemStackUtils;
import com.jeff_media.jefflib.PacketUtils;
import com.jeff_media.jefflib.ReflUtils;
import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import com.jeff_media.jefflib.data.ByteCounter;
import com.jeff_media.jefflib.data.Hologram;
import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import com.jeff_media.jefflib.internal.nms.v1_16_R1.ai.*;
import com.jeff_media.jefflib.internal.nms.v1_16_R1.ai.CustomGoalExecutor;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R1.util.CraftChatMessage;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.jeff_media.jefflib.internal.nms.v1_16_R1.NMS.*;

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
        for (final Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, new PacketPlayOutEntityMetadata(((Entity) entity).getId(), ((Entity) entity).getDataWatcher(), true));
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

        final PacketPlayOutEntityMetadata packetMeta = new PacketPlayOutEntityMetadata(((Entity) entity).getId(), ((Entity) entity).getDataWatcher(), true);
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
        throw new NMSNotSupportedException("1_16_R1 and older does not support this method");
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
        final WorldServer level = ((CraftWorld) world).getHandle();
        level.setDayTime(time);
        if (notifyPlayers) {
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
        return ((CraftServer) Bukkit.getServer()).getHandle().getServer().recentTps;
    }

    @Override
    public int getItemStackSizeInBytes(final org.bukkit.inventory.ItemStack itemStack) throws IOException {
        final ByteCounter counter = new ByteCounter();
        final NBTTagCompound tag = CraftItemStack.asNMSCopy(itemStack).getTag();
        if (tag == null) return ItemStackUtils.NO_DATA;
        tag.write(counter);
        return counter.getBytes();
    }

    @Override
    public String getDefaultWorldName() {
        return ((CraftServer) Bukkit.getServer()).getServer().propertyManager.getProperties().levelName;
    }

    @Override
    public PathfinderGoal createTemptGoal(final Creature entity, final Stream<org.bukkit.Material> materials, final double speed, final boolean canScare) {
        final EntityCreature pathfinderMob = asPathfinder(entity);
        return new HatchedTemptGoal(entity, pathfinderMob, ingredient(materials), speed, canScare);
    }

    @Override
    public PathfinderGoal createAvoidEntityGoal(final Creature entity, final Predicate<LivingEntity> predicate, final float maxDistance, final double walkSpeedModifier, final double sprintSpeedModifier) {
        return new HatchedAvoidEntityGoal(entity, asPathfinder(entity), predicate, maxDistance, walkSpeedModifier, sprintSpeedModifier);
    }

    @Override
    public PathfinderGoal createMoveToBlockGoal(final Creature entity, final Set<org.bukkit.Material> blocks, final double speed, final int searchRange, final int verticalSearchRange) {
        return new HatchedMoveToBlockGoal.ByMaterialSet(entity, asPathfinder(entity), speed, searchRange, verticalSearchRange, blocks);
    }

    @Override
    public PathfinderGoal createMoveToBlockGoal(final Creature entity, final Predicate<Block> blockPredicate, final double speed, final int searchRange, final int verticalSearchRange) {
        return new HatchedMoveToBlockGoal.ByBlockPredicate(entity, asPathfinder(entity), speed, searchRange, verticalSearchRange, blockPredicate);
    }

    @Override
    public void addGoal(final Mob entity, final PathfinderGoal goal, final int priority) {
        asMob(entity).goalSelector.a(priority, NMS.toNms(goal));
    }


    @Override
    public void removeGoal(final Mob entity, final PathfinderGoal goal) {
        asMob(entity).goalSelector.a(NMS.toNms(goal));
    }

    @Override
    public void removeAllGoals(final Mob entity) {
        ((Set<?>) ReflUtils.getFieldValue(PathfinderGoalSelector.class,"d", asMob(entity).goalSelector)).clear();
    }

    @Override
    public void addTargetGoal(final Mob entity, final PathfinderGoal goal, final int priority) {
        asMob(entity).targetSelector.a(priority, NMS.toNms(goal));
    }

    @Override
    public void removeTargetGoal(final Mob entity, final PathfinderGoal goal) {
        asMob(entity).targetSelector.a(NMS.toNms(goal));
    }

    @Override
    public void removeAllTargetGoals(final Mob entity) {
        ((Set<?>)ReflUtils.getFieldValue(PathfinderGoalSelector.class,"d", asMob(entity).targetSelector)).clear();
    }

    @Override
    public boolean moveTo(final Mob entity, final double x, final double y, final double z, final double speed) {
        final EntityInsentient pathfinderMob = asMob(entity);
        return pathfinderMob.getNavigation().a(x, y, z, speed);
    }

    @Override
    public boolean isServerRunnning() {
        return getDedicatedServer().isRunning();
    }

    @Override
    public com.jeff_media.jefflib.ai.goal.CustomGoalExecutor getCustomGoalExecutor(final CustomGoal customGoal, final Mob entity) {
        return new CustomGoalExecutor(customGoal, asMob(entity));
    }

    @Nullable
    @Override
    public Vector getRandomPos(final Creature entity, final int var1, final int var2) {
        final EntityCreature pathfinderMob = asPathfinder(entity);
        final Vec3D vec = RandomPositionGenerator.a(pathfinderMob, var1, var2); // could be .a or .b
        return vec == null ? null : new Vector(vec.x, vec.y, vec.z);
    }

    @Nullable
    @Override
    public Vector getRandomPosAway(final Creature entity, final int var1, final int var2, final Vector var3) {
        final EntityCreature pathfinderMob = asPathfinder(entity);
        final Vec3D vec = RandomPositionGenerator.c(pathfinderMob, var1, var2, toNms(var3)); // definitely c
        return vec == null ? null : toBukkit(vec);
    }

    @Nullable
    @Override
    public Vector getRandomPosTowards(final Creature entity, final int var1, final int var2, final Vector var3, final double var4) {
        final EntityCreature pathfinderMob = asPathfinder(entity);
        final Vec3D vec = RandomPositionGenerator.a(pathfinderMob, var1, var2, toNms(var3), var4); // a is the only that matches
        return vec == null ? null : toBukkit(vec);
    }

    @Nonnull
    @Override
    public MoveController getMoveControl(final Mob entity) {
        return new HatchedMoveController(asMob(entity).getControllerMove());
    }

    @Nonnull
    @Override
    public JumpController getJumpControl(final Mob entity) {
        return new HatchedJumpController(asMob(entity).getControllerJump());
    }

    @Nonnull
    @Override
    public LookController getLookControl(final Mob entity) {
        return new HatchedLookController(asMob(entity).getControllerLook());
    }

    @Nonnull
    @Override
    public PathNavigation getPathNavigation(final Mob entity) {
        final EntityInsentient pathfinderMob = asMob(entity);
        return new HatchedPathNavigation(pathfinderMob.getNavigation());
    }


}
