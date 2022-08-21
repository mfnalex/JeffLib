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

package com.jeff_media.jefflib.ai.navigation;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.internal.annotations.NMS;

@DoNotRename
public interface MoveController {

    @NMS
    boolean hasWanted();

    @NMS
    double getSpeedModifier();

    @NMS
    void setWantedPosition(double x, double y, double z, double speedModifier);

    @NMS
    void strafe(float forward, float right);

    @NMS
    double getWantedX();

    @NMS
    double getWantedY();

    @NMS
    double getWantedZ();

    enum Operation {
        WAIT, MOVE_TO, STRAFE, JUMPING
    }
}
