package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A {@link PathfinderGoal} that makes entities follow players who hold a certain item, similar to cows following wheat.
 * @see {@link Pathfinders#createTemptGoal(LivingEntity, Stream)}
 * @see {@link Pathfinders#createTemptGoal(LivingEntity, Collection)}
 * @see {@link Pathfinders#createTemptGoal(LivingEntity, Stream, double, boolean)}
 * @see {@link Pathfinders#createTemptGoal(LivingEntity, Collection, double, boolean)}
 */
public interface TemptGoal extends PathfinderGoal {



}
