package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Utility methods to create some custom {@link PathfinderGoal}s. For a custom goal, extend {@link CustomGoal}
 */
public interface PathfinderGoals {
    /**
     * Creates a <b>Tempt goal</b>, that will tempt animals using a certain set of items. This behaves like the vanilla
     * cow that gets tempted by wheat.
     * @param entity The entity to create the goal for
     * @param materials The items that can be used to tempt the entity
     * @param speedModifier The speed modifier to use when the entity is being tempted
     * @param canScare Whether the entity can be scared
     * @return The created goal
     */
    static PathfinderGoal temptGoal(@Nonnull final LivingEntity entity, @Nonnull final Collection<Material> materials, final double speedModifier, final boolean canScare) {
        return temptGoal(entity, materials.stream(), speedModifier, canScare);
    }

    /**
     * Creates a <b>Tempt goal</b>, that will tempt animals using a certain set of items. This behaves like the vanilla
     * cow that gets tempted by wheat.
     * @param entity The entity to create the goal for
     * @param materials The items that can be used to tempt the entity
     * @param speedModifier The speed modifier to use when the entity is being tempted
     * @param canScare Whether the entity can be scared
     * @return The created goal
     */
    static PathfinderGoal temptGoal(@Nonnull final LivingEntity entity, @Nonnull final Stream<Material> materials, final double speedModifier, final boolean canScare) {
        return JeffLib.getNMSHandler().createTemptGoal(entity, materials, speedModifier, canScare);
    }

    /**
     * Creates an <b>Avoid entity goal</b>, that will scare off the entity if the given Predicate matches. Players who are in {@link GameMode#SPECTATOR} or {@link GameMode#CREATIVE} will be ignored.
     * @param entity The entity to create the goal for
     * @param predicate The predicate to use to determine if the entity should be scared
     * @param maxDistance The maximum distance the entity can be from the player
     * @param walkSpeedModifier The walk speed modifier to use when the entity is being scared
     * @param sprintSpeedModifier The sprint speed modifier to use when the entity is being scared
     * @return The created goal
     */
    static PathfinderGoal avoidEntity(@Nonnull final LivingEntity entity, @Nonnull Predicate<LivingEntity> predicate, final float maxDistance, final double walkSpeedModifier, final double sprintSpeedModifier) {
        return JeffLib.getNMSHandler().createAvoidEntityGoal(entity, predicate, maxDistance, walkSpeedModifier, sprintSpeedModifier);
    }

    /**
     * Creates a <b>Move to block goal</b> that will make the entity move towards/stay at certain blocks
     * @param entity The entity to create the goal for
     * @param blockTypes The blocks that the entity should move towards. Must only contain Materials where {@link Material#isBlock()} is true.
     * @param speedModifier The speed modifier to use when the entity is moving towards a block
     * @param searchDistance The maximum distance the entity will look for matching blocks
     * @param verticalSearchDistance The maximum vertical distance the entity will look for matching blocks
     * @return The created goal
     */
    static PathfinderGoal moveToBlock(@Nonnull final LivingEntity entity, @Nonnull Set<Material> blockTypes, double speedModifier, int searchDistance, int verticalSearchDistance) {
        return JeffLib.getNMSHandler().createMoveToBlockGoal(entity, blockTypes, speedModifier, searchDistance, verticalSearchDistance);
    }

    /**
     * Creates a <b>Move to block goal</b> that will make the entity move towards/stay at certain blocks
     * @param entity The entity to create the goal for
     * @param blockPredicate The predicate to use to determine if the entity should move towards/stay at a block
     * @param speedModifier The speed modifier to use when the entity is moving towards a block
     * @param searchDistance The maximum distance the entity will look for matching blocks
     * @param verticalSearchDistance The maximum vertical distance the entity will look for matching blocks
     * @return The created goal
     */
    static PathfinderGoal moveToBlock(@Nonnull final LivingEntity entity, @Nonnull Predicate<Block> blockPredicate, double speedModifier, int searchDistance, int verticalSearchDistance) {
        return JeffLib.getNMSHandler().createMoveToBlockGoal(entity, blockPredicate, speedModifier, searchDistance, verticalSearchDistance);
    }

}
