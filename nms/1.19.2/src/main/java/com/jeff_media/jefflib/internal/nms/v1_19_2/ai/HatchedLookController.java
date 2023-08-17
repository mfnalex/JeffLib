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

package com.jeff_media.jefflib.internal.nms.v1_19_2.ai;

import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.internal.nms.v1_19_2.NMS;
import net.minecraft.world.entity.ai.control.LookControl;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class HatchedLookController implements LookController {

    private final LookControl lookControl;

    public HatchedLookController(final LookControl lookControl) {
        this.lookControl = lookControl;
    }

    @Override
    public void setLookAt(final Vector destination) {
        lookControl.setLookAt(NMS.toNms(destination));
    }

    @Override
    public void setLookAt(final Entity destination) {
        lookControl.setLookAt(NMS.toNms(destination));
    }

    @Override
    public void setLookAt(final Entity destination, final float yMaxRotSpeed, final float xMaxRotAngle) {
        lookControl.setLookAt(NMS.toNms(destination), yMaxRotSpeed, xMaxRotAngle);
    }

    @Override
    public void setLookAt(final double x, final double y, final double z) {
        lookControl.setLookAt(x, y, z);
    }

    @Override
    public void setLookAt(final double x, final double y, final double z, final float yMaxRotSpeed, final float xMaxRotAngle) {
        lookControl.setLookAt(x, y, z, yMaxRotSpeed, xMaxRotAngle);
    }

    @Override
    public boolean isLookingAtTarget() {
        return lookControl.isLookingAtTarget();
    }

    @Override
    public double getWantedX() {
        return lookControl.getWantedX();
    }

    @Override
    public double getWantedY() {
        return lookControl.getWantedY();
    }

    @Override
    public double getWantedZ() {
        return lookControl.getWantedZ();
    }
}
