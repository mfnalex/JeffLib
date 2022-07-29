package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.internal.nms.v1_19_R1.NMS;
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
