package com.jeff_media.jefflib.ai;

public interface PathNavigation {

    void stop();
    boolean moveTo(double x, double y, double z, double speedModifier);
}
