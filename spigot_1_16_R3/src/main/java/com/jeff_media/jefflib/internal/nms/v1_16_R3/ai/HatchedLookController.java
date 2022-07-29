package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.nms.v1_16_R3.NMS;
import net.minecraft.server.v1_16_R3.ControllerLook;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class HatchedLookController implements LookController {

    private final ControllerLook lookControl;

    public HatchedLookController(final ControllerLook lookControl) {
        this.lookControl = lookControl;
    }

    @Override
    public void setLookAt(final Vector destination) {
        lookControl.a(NMS.toNms(destination));
    }

    @Override
    public void setLookAt(final Entity destination) {
        throw new NMSNotSupportedException("1_16_R3 and older does not support this method");
    }

    @Override
    public void setLookAt(final Entity destination, final float yMaxRotSpeed, final float xMaxRotAngle) {
        lookControl.a(NMS.toNms(destination), yMaxRotSpeed, xMaxRotAngle);
    }

    @Override
    public void setLookAt(final double x, final double y, final double z) {
        lookControl.a(x, y, z);
    }

    @Override
    public void setLookAt(final double x, final double y, final double z, final float yMaxRotSpeed, final float xMaxRotAngle) {
        lookControl.a(x, y, z, yMaxRotSpeed, xMaxRotAngle);
    }

    @Override
    public boolean isLookingAtTarget() {
        throw new NMSNotSupportedException("1_17_R1 and older does not support this method");
    }

    @Override
    public double getWantedX() {
        return lookControl.d();
    }

    @Override
    public double getWantedY() {
        return lookControl.e();
    }

    @Override
    public double getWantedZ() {
        return lookControl.f();
    }
}
