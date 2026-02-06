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

package com.jeff_media.jefflib.ai.navigation;

import com.jeff_media.jefflib.internal.annotations.NMS;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;


public interface LookController {

    @NMS
    void setLookAt(Vector destination);

    @NMS("1.17")
    void setLookAt(Entity destination);

    @NMS
    void setLookAt(Entity destination, float yMaxRotSpeed, float xMaxRotAngle);

    @NMS
    void setLookAt(double x, double y, double z);

    @NMS
    void setLookAt(double x, double y, double z, float yMaxRotSpeed, float xMaxRotAngle);

    @NMS("1.18")
    boolean isLookingAtTarget();

    @NMS
    double getWantedX();

    @NMS
    double getWantedY();

    @NMS
    double getWantedZ();


}
