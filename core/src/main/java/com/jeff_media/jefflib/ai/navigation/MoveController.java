package com.jeff_media.jefflib.ai.navigation;

import com.jeff_media.jefflib.internal.annotations.NMS;

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
