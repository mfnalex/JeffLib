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

package com.jeff_media.jefflib.internal.nms.v1_18_R2.ai;

import com.jeff_media.jefflib.internal.nms.v1_18_R2.NMS;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.bukkit.util.BlockVector;

public class HatchedPathNavigation implements com.jeff_media.jefflib.ai.navigation.PathNavigation {

    private final PathNavigation navigation;

    public HatchedPathNavigation(final PathNavigation navigation) {
        this.navigation = navigation;
    }

    @Override
    public boolean moveTo(final double x, final double y, final double z, final double speedModifier) {
        return navigation.moveTo(x, y, z, speedModifier);
    }

    @Override
    public BlockVector getTargetPos() {
        return NMS.toBukkit(navigation.getTargetPos());
    }

    @Override
    public void setSpeedModifier(final double speedModifier) {
        navigation.setSpeedModifier(speedModifier);
    }

    @Override
    public void recomputePath() {
        navigation.recomputePath();
    }

    @Override
    public boolean isDone() {
        return navigation.isDone();
    }

    @Override
    public boolean isInProgress() {
        return navigation.isInProgress();
    }

    @Override
    public void stop() {
        navigation.stop();
    }

    @Override
    public boolean isStableDestination(final BlockVector pos) {
        return navigation.isStableDestination(NMS.toNms(pos));
    }

    @Override
    public void setCanFloat(final boolean canFloat) {
        navigation.setCanFloat(canFloat);
    }

    @Override
    public boolean shouldRecomputePath(final BlockVector pos) {
        return navigation.shouldRecomputePath(NMS.toNms(pos));
    }

    @Override
    public float getMaxDistanceToWaypoint() {
        return navigation.getMaxDistanceToWaypoint();
    }

    @Override
    public boolean isStuck() {
        return navigation.isStuck();
    }
}
