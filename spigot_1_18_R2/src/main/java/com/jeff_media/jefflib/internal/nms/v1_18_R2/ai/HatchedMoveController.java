package com.jeff_media.jefflib.internal.nms.v1_18_R2.ai;

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
