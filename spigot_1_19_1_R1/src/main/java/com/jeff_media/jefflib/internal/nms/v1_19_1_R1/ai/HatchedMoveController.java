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

package com.jeff_media.jefflib.internal.nms.v1_19_1_R1.ai;

import com.jeff_media.jefflib.ai.navigation.MoveController;

public class HatchedMoveController implements MoveController {

    private final net.minecraft.world.entity.ai.control.MoveControl moveControl;

    public HatchedMoveController(final net.minecraft.world.entity.ai.control.MoveControl moveControl) {
        this.moveControl = moveControl;
    }

    @Override
    public boolean hasWanted() {
        return moveControl.hasWanted();
    }

    @Override
    public double getSpeedModifier() {
        return moveControl.getSpeedModifier();
    }

    @Override
    public void setWantedPosition(final double x, final double y, final double z, final double speedModifier) {
        moveControl.setWantedPosition(x, y, z, speedModifier);
    }

    @Override
    public void strafe(final float forward, final float right) {
        moveControl.strafe(forward, right);
    }

    @Override
    public double getWantedX() {
        return moveControl.getWantedX();
    }

    @Override
    public double getWantedY() {
        return moveControl.getWantedY();
    }

    @Override
    public double getWantedZ() {
        return moveControl.getWantedZ();
    }
}
