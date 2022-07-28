package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

public interface TemptGoal extends PathfinderGoal {

    static TemptGoal create(LivingEntity entity, Collection<Material> materials, double speed, boolean canScare) {
        return create(entity, materials.stream(), speed, canScare);
    }

    static TemptGoal create(LivingEntity entity, Stream<Material> materials, double speed, boolean canScare) {
        return JeffLib.getNMSHandler().createTemptGoal(entity, materials, speed, canScare);
    }

    static TemptGoal create(LivingEntity entity, Collection<Material> materials) {
        return create(entity, materials.stream());
    }

    static TemptGoal create(LivingEntity entity, Stream<Material> materials) {
        final AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        double speed = 1.0D;
        if(attribute == null) {
            speed = attribute.getValue();
        }
        return create(entity, materials, speed, false);
    }

}
