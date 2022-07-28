package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.EntityUtils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public abstract class CustomGoal implements PathfinderGoal {

    private final LivingEntity bukkitEntity;

    protected CustomGoal(LivingEntity bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public abstract boolean canUse();

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    public boolean moveTo(double x, double y, double z) {
        return EntityUtils.walkTo(bukkitEntity, x, y, z);
    }

    public boolean moveTo(double x, double y, double z, double speed) {
        return EntityUtils.walkTo(bukkitEntity, x, y, z, speed);
    }

    public boolean moveTo(Location location) {
        return EntityUtils.walkTo(bukkitEntity, location);
    }

    public boolean moveTo(Location location, double speed) {
        return EntityUtils.walkTo(bukkitEntity, location, speed);
    }
}
