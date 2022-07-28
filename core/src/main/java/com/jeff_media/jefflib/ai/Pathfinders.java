package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.JeffLib;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Utility methods to create some custom {@link PathfinderGoal}s
 */
public interface Pathfinders {

    static TemptGoal createTemptGoal(LivingEntity entity, Collection<Material> materials, double speed, boolean canScare) {
        return createTemptGoal(entity, materials.stream(), speed, canScare);
    }

    static TemptGoal createTemptGoal(LivingEntity entity, Stream<Material> materials, double speed, boolean canScare) {
        return JeffLib.getNMSHandler().createTemptGoal(entity, materials, speed, canScare);
    }

    static TemptGoal createTemptGoal(LivingEntity entity, Collection<Material> materials) {
        return createTemptGoal(entity, materials.stream());
    }

    static TemptGoal createTemptGoal(LivingEntity entity, Stream<Material> materials) {
        final AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        double speed = 1.0D;
        if(attribute == null) {
            speed = attribute.getValue();
        }
        return createTemptGoal(entity, materials, speed, false);
    }

}
