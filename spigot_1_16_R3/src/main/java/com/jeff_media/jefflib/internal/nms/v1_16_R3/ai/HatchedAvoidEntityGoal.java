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

package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import com.jeff_media.jefflib.internal.nms.v1_16_R3.NMS;
import java.util.function.Predicate;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;

public class HatchedAvoidEntityGoal extends PathfinderGoalAvoidTarget<EntityLiving> implements PathfinderGoal {

    private final org.bukkit.entity.Creature bukkitEntity;

    public HatchedAvoidEntityGoal(final org.bukkit.entity.Creature bukkitEntity, final EntityCreature mob, final Predicate<org.bukkit.entity.LivingEntity> bukkitPredicate, final float maxDistance, final double walkSpeedModifier, final double sprintSpeedModifier) {
        super(mob, EntityLiving.class, maxDistance, walkSpeedModifier, sprintSpeedModifier, livingEntity -> bukkitPredicate.test(NMS.toBukkit(livingEntity)));
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public org.bukkit.entity.Creature getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public boolean canUse() {
        return a();
    }
}
