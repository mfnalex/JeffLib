/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.internal.nms.v1.21.4;

import static com.jeff_media.jefflib.ItemStackUtils.NO_DATA;
import static com.jeff_media.jefflib.internal.nms.v1.21.4.NMS.*;

import com.jeff_media.jefflib.ItemStackSerializer;
import com.jeff_media.jefflib.ItemStackUtils;
import com.jeff_media.jefflib.PacketUtils;
import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.CustomGoalExecutor;
import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import com.jeff_media.jefflib.data.ByteCounter;
import com.jeff_media.jefflib.data.Hologram;
import com.jeff_media.jefflib.data.OfflinePlayerPersistentDataContainer;
import com.jeff_media.jefflib.data.SerializedEntity;
import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.exceptions.UseApiNowException;
import com.jeff_media.jefflib.internal.annotations.Tested;
import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import com.jeff_media.jefflib.internal.nms.BukkitUnsafe;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedAvoidEntityGoal;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedJumpController;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedLookController;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedMoveToBlockGoal;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedTemptGoal;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedMoveController;
import com.jeff_media.jefflib.internal.nms.v1.21.4.ai.HatchedPathNavigation;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
//import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_21_R2.CraftServer;
import org.bukkit.craftbukkit.v1_21_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_21_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_21_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_21_R2.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_21_R2.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_21_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public void changeNMSEntityName(@NotNull final Object entity, @NotNull final String name) { // TODO Check if this works
        ((Entity) entity).setCustomName(CraftChatMessage.fromString(name)[0]);
        for (final org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
            SynchedEntityData data = ((Entity) entity).getEntityData();
            sendPacket(player, new ClientboundSetEntityDataPacket(((Entity) entity).getId(), data.getNonDefaultValues()));
        }
    }

    @Override
    public Object createHologram(@NotNull final Location location, @NotNull final String line, @NotNull final Hologram.Type type) {
        final CraftWorld craftWorld = (CraftWorld) location.getWorld();
        final ServerLevel world = Objects.requireNonNull(craftWorld).getHandle();
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
    public void showEntityToPlayer(@NotNull final Object entity, @NotNull final org.bukkit.entity.Player player) {
        PacketUtils.sendPacket(player, new ClientboundAddEntityPacket((Entity) entity, 0, ((Entity) entity).blockPosition()));
        PacketUtils.sendPacket(player, new ClientboundSetEntityDataPacket(((Entity) entity).getId(), ((Entity) entity).getEntityData().getNonDefaultValues()));
    }

    @Override
    public void hideEntityFromPlayer(@NotNull final Object entity, @NotNull final org.bukkit.entity.Player player) {
        PacketUtils.sendPacket(player, new ClientboundRemoveEntitiesPacket(((Entity) entity).getId()));
    }

    @Override
    public void sendPacket(@NotNull final org.bukkit.entity.Player player, @NotNull final Object packet) {
        NMSPacketUtils.sendPacket(player, packet);
    }

    @Override
    public Pair<String, String> getBiomeName(@NotNull final Location location) {
        return NMSBiomeUtils.getBiomeName(location);
    }

    @Override
    public void playTotemAnimation(@NotNull final org.bukkit.entity.Player player) {
        final ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Packet<?> packet = new ClientboundEntityEventPacket(entityPlayer, (byte) 35);
        NMSPacketUtils.sendPacket(entityPlayer, packet);
    }

    @Override
    public void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile) {
        final ServerLevel world = ((CraftWorld) block.getWorld()).getHandle();
        final BlockPos blockPosition = new BlockPos(block.getX(), block.getY(), block.getZ());
        final SkullBlockEntity skull = (SkullBlockEntity) world.getBlockEntity(blockPosition, false);
        assert skull != null;
        skull.setOwner(new ResolvableProfile(gameProfile));
    }

    @Override
    public void setFullTimeWithoutTimeSkipEvent(@NotNull final World world, final long time, final boolean notifyPlayers) {
        final ServerLevel level = ((CraftWorld) world).getHandle();
        level.setDayTime(time);
        if (notifyPlayers) {
            for (final Player player : world.getPlayers()) {
                final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
                if (serverPlayer.connection != null) {
                    try (Level level2 = serverPlayer.level()) {
                        serverPlayer.connection.send(new ClientboundSetTimePacket(level2.getGameTime(), serverPlayer.getPlayerTime(), Boolean.TRUE.equals(level2.getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE))));
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
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
        ByteCounter counter = new ByteCounter();
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return NO_DATA;
        counter.writeBytes(itemStack.getItemMeta().getAsString());
        return counter.getBytes();
    }

    @Override
    public String getDefaultWorldName() {
        return ((CraftServer) Bukkit.getServer()).getServer().getProperties().levelName;
    }

    @Override
    public PathfinderGoal createTemptGoal(final Creature entity, final Stream<Material> materials, final double speed, final boolean canScare) {
        return new HatchedTemptGoal(entity, asPathfinder(entity), ingredient(materials), speed, canScare);
    }

    @Override
    public PathfinderGoal createAvoidEntityGoal(final Creature entity, final Predicate<LivingEntity> predicate, final float maxDistance, final double walkSpeedModifier, final double sprintSpeedModifier) {
        return new HatchedAvoidEntityGoal(entity, asPathfinder(entity), predicate, maxDistance, walkSpeedModifier, sprintSpeedModifier);
    }

    @Override
    public PathfinderGoal createMoveToBlockGoal(final Creature entity, final Set<Material> blocks, final double speed, final int searchRange, final int verticalSearchRange) {
        return new HatchedMoveToBlockGoal.ByMaterialSet(entity, asPathfinder(entity), speed, searchRange, verticalSearchRange, blocks);
    }

    @Override
    public PathfinderGoal createMoveToBlockGoal(final Creature entity, final Predicate<Block> blockPredicate, final double speed, final int searchRange, final int verticalSearchRange) {
        return new HatchedMoveToBlockGoal.ByBlockPredicate(entity, asPathfinder(entity), speed, searchRange, verticalSearchRange, blockPredicate);
    }

    @Override
    public void addGoal(final Mob entity, final PathfinderGoal goal, final int priority) {
        asMob(entity).goalSelector.addGoal(priority, toNms(goal));
    }


    @Override
    public void removeGoal(final Mob entity, final PathfinderGoal goal) {
        asMob(entity).goalSelector.removeGoal(toNms(goal));
    }

    @Override
    public void removeAllGoals(final Mob entity) {
        asMob(entity).goalSelector.removeAllGoals(__ -> true);
    }

    @Override
    public void addTargetGoal(final Mob entity, final PathfinderGoal goal, final int priority) {
        asMob(entity).targetSelector.addGoal(priority, toNms(goal));
    }

    @Override
    public void removeTargetGoal(final Mob entity, final PathfinderGoal goal) {
        asMob(entity).targetSelector.removeGoal(toNms(goal));
    }

    @Override
    public void removeAllTargetGoals(final Mob entity) {
        asMob(entity).targetSelector.removeAllGoals(__ -> true);
    }

    @Override
    public boolean moveTo(final Mob entity, final double x, final double y, final double z, final double speed) {
        final net.minecraft.world.entity.Mob pathfinderMob = asMob(entity);
        return pathfinderMob.getNavigation().moveTo(x, y, z, speed);
    }

    @Override
    public boolean isServerRunnning() {
        return getDedicatedServer().isRunning();
    }

    @Override
    public CustomGoalExecutor getCustomGoalExecutor(final CustomGoal customGoal, final Mob entity) {
        return new com.jeff_media.jefflib.internal.nms.v1.21.4.ai.CustomGoalExecutor(customGoal, asMob(entity));
    }

    @Nullable
    @Override
    public Vector getRandomPos(final Creature entity, final int var1, final int var2) {
        final PathfinderMob pathfinderMob = asPathfinder(entity);
        final Vec3 vec = DefaultRandomPos.getPos(pathfinderMob, var1, var2);
        return vec == null ? null : new Vector(vec.x, vec.y, vec.z);
    }

    @Nullable
    @Override
    public Vector getRandomPosAway(final Creature entity, final int var1, final int var2, final Vector var3) {
        final PathfinderMob pathfinderMob = asPathfinder(entity);
        final Vec3 vec = DefaultRandomPos.getPosAway(pathfinderMob, var1, var2, toNms(var3));
        return vec == null ? null : toBukkit(vec);
    }

    @Nullable
    @Override
    public Vector getRandomPosTowards(final Creature entity, final int var1, final int var2, final Vector var3, final double var4) {
        final PathfinderMob pathfinderMob = asPathfinder(entity);
        final Vec3 vec = DefaultRandomPos.getPosTowards(pathfinderMob, var1, var2, toNms(var3), var4);
        return vec == null ? null : toBukkit(vec);
    }

    @NotNull
    @Override
    public MoveController getMoveControl(final Mob entity) {
        return new HatchedMoveController(asMob(entity).getMoveControl());
    }

    @NotNull
    @Override
    public JumpController getJumpControl(final Mob entity) {
        return new HatchedJumpController(asMob(entity).getJumpControl());
    }

    @NotNull
    @Override
    public LookController getLookControl(final Mob entity) {
        return new HatchedLookController(asMob(entity).getLookControl());
    }

    @NotNull
    @Override
    public PathNavigation getPathNavigation(final org.bukkit.entity.Mob entity) {
        final net.minecraft.world.entity.Mob pathfinderMob = asMob(entity);
        return new HatchedPathNavigation(pathfinderMob.getNavigation());
    }

    @Tested("1.19.4")
    @Nullable
    @Override
    public Advancement loadVolatileAdvancement(final NamespacedKey key, final String advancement) {
//        final ResourceLocation resourceLocation = CraftNamespacedKey.toMinecraft(key);
//        final JsonElement jsonelement = ServerAdvancementManager.GSON.fromJson(advancement, JsonElement.class);
//        final JsonObject jsonobject = GsonHelper.convertToJsonObject(jsonelement, "advancement");
//        net.minecraft.advancements.Advancement nms = net.minecraft.advancements.Advancement.fromJson(jsonobject, new DeserializationContext(resourceLocation, getServer().getLootData()));
//        getServer().getAdvancements().advancements.put(resourceLocation, new AdvancementHolder(resourceLocation, nms));
//        return Bukkit.getAdvancement(key);
        throw new NMSNotSupportedException("Only available in 1.20.2 and earlier"); // TODO
    }

    @NotNull
    @Override
    public BukkitUnsafe getUnsafe() {
        return com.jeff_media.jefflib.internal.nms.v1.21.4.BukkitUnsafe.INSTANCE;
    }

    @Override
    public String serializePdc(PersistentDataContainer pdc) {
        return ((CraftPersistentDataContainer) pdc).toTagCompound().getAsString();
    }

    @Override
    public void deserializePdc(String serializedPdc, PersistentDataContainer target) throws Exception {
        CompoundTag tag = TagParser.parseTag(serializedPdc);
        ((CraftPersistentDataContainer) target).putAll(tag);
    }

    @Override
    public void respawnPlayer(Player player) {
        getServer().getPlayerList().respawn(toNms(player), true, Entity.RemovalReason.KILLED, PlayerRespawnEvent.RespawnReason.PLUGIN);
    }

    @Override
    public SerializedEntity serialize(org.bukkit.entity.Entity entity) {
        CompoundTag tag = new CompoundTag();
        toNms(entity).saveWithoutId(tag);
        return new SerializedEntity(entity.getType(), tag.getAsString());
    }

    @Override
    public void applyNbt(org.bukkit.entity.Entity entity, String nbtData) {
        CompoundTag tag = null;
        try {
            tag = TagParser.parseTag(nbtData);
        } catch (CommandSyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        toNms(entity).load(tag);
    }

    @Override
    public OfflinePlayerPersistentDataContainer getPDCFromDatFile(File file) throws IOException {
        CraftPersistentDataTypeRegistry registry = new CraftPersistentDataTypeRegistry();
        CraftPersistentDataContainer container = new CraftPersistentDataContainer(registry);
        CompoundTag fileTag = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());
        CompoundTag pdcTag = fileTag.getCompound("BukkitValues");
        container.putAll(pdcTag);
        return new OfflinePlayerPersistentDataContainer(container, file, fileTag);
    }

    @Override
    public void updatePdcInDatFile(OfflinePlayerPersistentDataContainer pdc) throws IOException {
        CompoundTag pdcTag = ((CraftPersistentDataContainer) pdc.getCraftPersistentDataContainer()).toTagCompound();
        CompoundTag fileTag = (CompoundTag) pdc.getCompoundTag();
        fileTag.put("BukkitValues", pdcTag);
        NbtIo.writeCompressed(fileTag, pdc.getFile().toPath());
    }

}
