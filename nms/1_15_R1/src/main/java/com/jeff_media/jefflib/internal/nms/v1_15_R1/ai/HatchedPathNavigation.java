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

package com.jeff_media.jefflib.internal.nms.v1_15_R1.ai;

import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.nms.v1_15_R1.NMS;
import net.minecraft.server.v1_15_R1.NavigationAbstract;
import org.bukkit.util.BlockVector;

public class HatchedPathNavigation implements PathNavigation {

    private final NavigationAbstract navigation;

    public HatchedPathNavigation(final NavigationAbstract navigation) {
        this.navigation = navigation;
    }

    @Override
    public boolean moveTo(final double x, final double y, final double z, final double speedModifier) {
        return navigation.a(x, y, z, speedModifier);
    }

    @Override
    public BlockVector getTargetPos() {
        return NMS.toBukkit(navigation.h());
    }

    @Override
    public void setSpeedModifier(final double speedModifier) {
        navigation.a(speedModifier);
    }

    @Override
    public void recomputePath() {
        navigation.j();
    }

    @Override
    public boolean isDone() {
        return navigation.m();
    }

    @Override
    public boolean isInProgress() {
        return navigation.n();
    }

    @Override
    public void stop() {
        navigation.o();
    }

    @Override
    public boolean isStableDestination(final BlockVector pos) {
        return navigation.a(NMS.toNms(pos));
    }

    @Override
    public void setCanFloat(final boolean canFloat) {
        navigation.d(canFloat);
    }

    @Override
    public boolean shouldRecomputePath(final BlockVector pos) {
        throw new NMSNotSupportedException("1_17_R1 and older does not support this method");
    }

    @Override
    public float getMaxDistanceToWaypoint() {
        throw new NMSNotSupportedException("1_16_R3 and older does not support this method");
    }

    @Override
    public boolean isStuck() {
        throw new NMSNotSupportedException("1_15_R1 and older does not support this method");
    }
}
