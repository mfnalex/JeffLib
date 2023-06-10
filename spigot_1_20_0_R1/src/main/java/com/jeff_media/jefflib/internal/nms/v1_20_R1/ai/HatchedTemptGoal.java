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

package com.jeff_media.jefflib.internal.nms.v1_20_R1.ai;

import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.entity.Creature;


public class HatchedTemptGoal extends TemptGoal implements PathfinderGoal {

    private final Creature bukkitEntity;

    public HatchedTemptGoal(final Creature entity, final PathfinderMob pathfinderMob, final Ingredient ingredient, final double speed, final boolean canScare) {
        super(pathfinderMob, speed, ingredient, canScare);
        this.bukkitEntity = entity;
    }

    @Override
    public Creature getBukkitEntity() {
        return bukkitEntity;
    }

}
