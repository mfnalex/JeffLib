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

package com.jeff_media.jefflib.internal.nms.v1_17_R1;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import com.jeff_media.jefflib.internal.nms.AbstractNMSTranslationKeyProvider;
import com.jeff_media.jefflib.internal.nms.BukkitUnsafe;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedAvoidEntityGoal;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedJumpController;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedLookController;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedMoveController;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedMoveToBlockGoal;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedPathNavigation;
import com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.HatchedTemptGoal;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_17_R1.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftNamespacedKey;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.jeff_media.jefflib.internal.nms.v1_17_R1.NMS.asMob;
import static com.jeff_media.jefflib.internal.nms.v1_17_R1.NMS.asPathfinder;
import static com.jeff_media.jefflib.internal.nms.v1_17_R1.NMS.getDedicatedServer;
import static com.jeff_media.jefflib.internal.nms.v1_17_R1.NMS.ingredient;
import static com.jeff_media.jefflib.internal.nms.v1_17_R1.NMS.toBukkit;
import static com.jeff_media.jefflib.internal.nms.v1_17_R1.NMS.toNms;

public class NMSHandler implements AbstractNMSHandler, AbstractNMSTranslationKeyProvider {

    private final MaterialHandler materialHandler = new MaterialHandler();
    private final BlockHandler blockHandler = new BlockHandler();
    private final com.jeff_media.jefflib.internal.nms.v1_17_R1.BukkitUnsafe unsafe = com.jeff_media.jefflib.internal.nms.v1_17_R1.BukkitUnsafe.INSTANCE;

    @Override
    public AbstractNMSMaterialHandler getMaterialHandler() {
        return materialHandler;
    }

    @Override
    public AbstractNMSBlockHandler getBlockHandler() {
        return blockHandler;
    }

    @Override
    public void changeNMSEntityName(@NotNull final Object entity, @NotNull final String name) {
        ((Entity) entity).setCustomName(CraftChatMessage.fromString(name)[0]);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, new ClientboundSetEntityDataPacket(((Entity) entity).getId(), ((Entity) entity).getEntityData(), true));
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
    public void showEntityToPlayer(@NotNull final Object entity, @NotNull final Player player) {
        PacketUtils.sendPacket(player, new ClientboundAddEntityPacket((Entity) entity));
        PacketUtils.sendPacket(player, new ClientboundSetEntityDataPacket(((Entity) entity).getId(), ((Entity) entity).getEntityData(), true));
    }

    @Override
    public void hideEntityFromPlayer(@NotNull final Object entity, @NotNull final Player player) {
        PacketUtils.sendPacket(player, new ClientboundRemoveEntitiesPacket(((Entity) entity).getId()));
    }

    @Override
    public void sendPacket(@NotNull final Player player, @NotNull final Object packet) {
        NMSPacketUtils.sendPacket(player, packet);
    }

    @Override
    public Pair<String, String> getBiomeName(@NotNull final Location location) {
        return NMSBiomeUtils.getBiomeName(location);
    }

    @Override
    public void playTotemAnimation(@NotNull final Player player) {
        final ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Packet<?> packet = new ClientboundEntityEventPacket(entityPlayer, (byte) 35);
        final Connection playerConnection = entityPlayer.connection.connection;
        playerConnection.send(packet);
    }

    @Override
    public void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile) {
        final ServerLevel world = ((CraftWorld) block.getWorld()).getHandle();
        final BlockPos blockPosition = new BlockPos(block.getX(), block.getY(), block.getZ());
        final SkullBlockEntity skull = (SkullBlockEntity) world.getBlockEntity(blockPosition);
        assert skull != null;
        skull.setOwner(gameProfile);
    }

    @Override
    public String itemStackToJson(@NotNull final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        final CompoundTag compoundTag = new CompoundTag();
        nmsItemStack.save(compoundTag);
        return compoundTag.getAsString();
    }

    @Override
    public org.bukkit.inventory.ItemStack itemStackFromJson(@NotNull String json) throws Exception {
        final CompoundTag compoundTag = TagParser.parseTag(json);
        final ItemStack nmsItemStack = ItemStack.of(compoundTag);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public void setFullTimeWithoutTimeSkipEvent(@NotNull final World world, final long time, final boolean notifyPlayers) {
        final ServerLevel level = ((CraftWorld) world).getHandle();
        level.setDayTime(time);
        if (notifyPlayers) {
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
        return ((CraftServer) Bukkit.getServer()).getHandle().getServer().recentTps;
    }

    @Override
    public int getItemStackSizeInBytes(final org.bukkit.inventory.ItemStack itemStack) throws IOException {
        final ByteCounter counter = new ByteCounter();
        final CompoundTag tag = CraftItemStack.asNMSCopy(itemStack).getTag();
        if (tag == null) return ItemStackUtils.NO_DATA;
        tag.write(counter);
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
        asMob(entity).goalSelector.addGoal(priority, NMS.toNms(goal));
    }


    @Override
    public void removeGoal(final Mob entity, final PathfinderGoal goal) {
        asMob(entity).goalSelector.removeGoal(NMS.toNms(goal));
    }

    @Override
    public void removeAllGoals(final Mob entity) {
        asMob(entity).goalSelector.removeAllGoals();
    }

    @Override
    public void addTargetGoal(final Mob entity, final PathfinderGoal goal, final int priority) {
        asMob(entity).targetSelector.addGoal(priority, NMS.toNms(goal));
    }

    @Override
    public void removeTargetGoal(final Mob entity, final PathfinderGoal goal) {
        asMob(entity).targetSelector.removeGoal(NMS.toNms(goal));
    }

    @Override
    public void removeAllTargetGoals(final Mob entity) {
        asMob(entity).targetSelector.removeAllGoals();
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
        return new com.jeff_media.jefflib.internal.nms.v1_17_R1.ai.CustomGoalExecutor(customGoal, asMob(entity));
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
    public PathNavigation getPathNavigation(final Mob entity) {
        final net.minecraft.world.entity.Mob pathfinderMob = asMob(entity);
        return new HatchedPathNavigation(pathfinderMob.getNavigation());
    }

    @Nullable
    @Override
    public Advancement loadVolatileAdvancement(final NamespacedKey key, final String advancement) {
        final ResourceLocation resourceLocation = CraftNamespacedKey.toMinecraft(key);
        final JsonElement jsonelement = ServerAdvancementManager.GSON.fromJson(advancement, JsonElement.class);
        final JsonObject jsonobject = GsonHelper.convertToJsonObject(jsonelement, "advancement");
        net.minecraft.advancements.Advancement.Builder nms = net.minecraft.advancements.Advancement.Builder.fromJson(jsonobject, new DeserializationContext(resourceLocation, NMS.getServer().getPredicateManager()));
        NMS.getServer().getAdvancements().advancements.add(Maps.newHashMap(Collections.singletonMap(resourceLocation, nms)));
        return Bukkit.getAdvancement(key);
    }

    @NotNull
    @Override
    public BukkitUnsafe getUnsafe() {
        return com.jeff_media.jefflib.internal.nms.v1_17_R1.BukkitUnsafe.INSTANCE;
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
        NMS.getServer().getPlayerList().respawn(NMS.toNms(player), true);
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
    public String getItemTranslationKey(Material mat) {
        return unsafe.getNMSItemFromMaterial(mat).getDescriptionId();
    }

    @Override
    public String getBlockTranslationKey(Material mat) {
        return unsafe.getNMSBlockFromMaterial(mat).getDescriptionId();
    }

    @Override
    public String getTranslationKey(Block block) {
        return toNms(block).getDescriptionId();
    }

    @Override
    public String getTranslationKey(EntityType entityType) {
        return net.minecraft.world.entity.EntityType.byString(entityType.getName())
                .map(net.minecraft.world.entity.EntityType::getDescriptionId)
                .orElse(null);
    }

    @Override
    public String getTranslationKey(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        return nmsItemStack.getItem().getDescriptionId(nmsItemStack);
    }

    @Override
    public OfflinePlayerPersistentDataContainer getPDCFromDatFile(File file) throws IOException {
        CraftPersistentDataTypeRegistry registry = new CraftPersistentDataTypeRegistry();
        CraftPersistentDataContainer container = new CraftPersistentDataContainer(registry);
        CompoundTag fileTag = NbtIo.readCompressed(file);
        CompoundTag pdcTag = fileTag.getCompound("BukkitValues");
        container.putAll(pdcTag);
        return new OfflinePlayerPersistentDataContainer(container, file, fileTag);
    }

    @Override
    public void updatePdcInDatFile(OfflinePlayerPersistentDataContainer pdc) throws IOException {
        CompoundTag pdcTag = ((CraftPersistentDataContainer) pdc.getCraftPersistentDataContainer()).toTagCompound();
        CompoundTag fileTag = (CompoundTag) pdc.getCompoundTag();
        fileTag.put("BukkitValues", pdcTag);
        NbtIo.writeCompressed(fileTag, pdc.getFile());
    }

}
