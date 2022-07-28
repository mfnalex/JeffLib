package com.jeff_media.jefflib.internal.nms.v1_16_R2.ai;

import com.jeff_media.jefflib.ai.MoveController;
import net.minecraft.server.v1_16_R2.ControllerMove;

public class HatchedMoveController implements MoveController {

    private final ControllerMove moveControl;

    public HatchedMoveController(final ControllerMove moveControl) {
        this.moveControl = moveControl;
    }

    @Override
    public boolean hasWanted() {
        return moveControl.b();
    }

    @Override
    public double getSpeedModifier() {
        return moveControl.c();
    }

    @Override
    public void setWantedPosition(final double x, final double y, final double z, final double speedModifier) {
        moveControl.a(x, y, z, speedModifier);
    }

    @Override
    public void strafe(final float forward, final float right) {
        moveControl.a(forward, right);
    }

    @Override
    public double getWantedX() {
        return moveControl.d();
    }

    @Override
    public double getWantedY() {
        return moveControl.e();
    }

    @Override
    public double getWantedZ() {
        return moveControl.f();
    }
}
