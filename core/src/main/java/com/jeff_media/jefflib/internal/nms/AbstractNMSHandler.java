/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.internal.nms;

import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.CustomGoalExecutor;
import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import com.jeff_media.jefflib.data.Hologram;
import com.jeff_media.jefflib.data.OfflinePlayerPersistentDataContainer;
import com.jeff_media.jefflib.data.SerializedEntity;
import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.internal.annotations.Internal;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;

/**
 * Abstract NMS handler, for internal use only.
 *
 * @internal For internal use only
 */
@Internal
public interface AbstractNMSHandler {

    //void updateMap(@Nonnull final MapView map);

    AbstractNMSMaterialHandler getMaterialHandler();

    AbstractNMSBlockHandler getBlockHandler();

    void changeNMSEntityName(@Nonnull Object entity, @Nonnull String name);

    Object createHologram(@Nonnull Location location, @Nonnull String line, @Nonnull Hologram.Type type);

    void showEntityToPlayer(@Nonnull Object entity, @Nonnull Player player);

    void hideEntityFromPlayer(@Nonnull Object entity, @Nonnull Player player);

    void sendPacket(@Nonnull final Player player, @Nonnull final Object packet);

    Pair<String, String> getBiomeName(@Nonnull final Location location);

    void playTotemAnimation(@Nonnull final Player player);

    void setHeadTexture(@Nonnull final Block block, @Nonnull final GameProfile gameProfile);

    String itemStackToJson(@Nonnull final ItemStack itemStack);

    void setFullTimeWithoutTimeSkipEvent(@Nonnull final World world, final long time, final boolean notifyPlayers);

    double[] getTps();

    int getItemStackSizeInBytes(ItemStack itemStack) throws IOException;

    String getDefaultWorldName();

    PathfinderGoal createTemptGoal(Creature entity, Stream<Material> materials, double speed, boolean canScare);

    PathfinderGoal createAvoidEntityGoal(Creature entity, Predicate<LivingEntity> predicate, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier);

    PathfinderGoal createMoveToBlockGoal(Creature entity, Set<Material> blocks, double speed, int searchRange, int verticalSearchRange);

    PathfinderGoal createMoveToBlockGoal(Creature entity, Predicate<Block> blockPredicate, double speed, int searchRange, int verticalSearchRange);

    void addGoal(Mob entity, PathfinderGoal goal, int priority);

    void removeGoal(Mob entity, PathfinderGoal goal);

    void removeAllGoals(Mob entity);

    void addTargetGoal(Mob entity, PathfinderGoal goal, int priority);

    void removeTargetGoal(Mob entity, PathfinderGoal goal);

    void removeAllTargetGoals(Mob entity);

    boolean moveTo(Mob entity, double x, double y, double z, double speed);

    boolean isServerRunnning();

    CustomGoalExecutor getCustomGoalExecutor(CustomGoal customGoal, Mob entity);

    @Nullable
    Vector getRandomPos(Creature entity, int var1, int var2);

    @Nullable
    Vector getRandomPosAway(Creature entity, int var1, int var2, Vector var3);

    @Nullable
    Vector getRandomPosTowards(Creature entity, int var1, int var2, Vector var3, double var4);

    @Nonnull
    MoveController getMoveControl(Mob entity);

    @Nonnull
    JumpController getJumpControl(Mob entity);

    @Nonnull
    LookController getLookControl(Mob entity);

    @Nonnull
    PathNavigation getPathNavigation(Mob entity);

    @Nullable
    Advancement loadVolatileAdvancement(NamespacedKey key, String advancement);

    @Nonnull
    BukkitUnsafe getUnsafe();

    String serializePdc(PersistentDataContainer pdc);

    void deserializePdc(String serializedPdc, PersistentDataContainer target) throws Exception;

    void respawnPlayer(Player player);

    SerializedEntity serialize(Entity entity);

    void applyNbt(Entity entity, String nbtData);

    OfflinePlayerPersistentDataContainer getPDCFromDatFile(File file) throws IOException;

    void updatePdcInDatFile(OfflinePlayerPersistentDataContainer pdc) throws IOException;

}
