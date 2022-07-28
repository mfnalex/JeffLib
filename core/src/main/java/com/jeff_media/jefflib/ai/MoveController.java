package com.jeff_media.jefflib.ai;

public interface MoveController {

    boolean hasWanted();

    double getSpeedModifier();

    void setWantedPosition(double x, double y, double z, double speedModifier);

    void strafe(float forward, float right);

    double getWantedX();

    double getWantedY();

    double getWantedZ();

    enum Operation {
        WAIT, MOVE_TO, STRAFE, JUMPING
    }
}
