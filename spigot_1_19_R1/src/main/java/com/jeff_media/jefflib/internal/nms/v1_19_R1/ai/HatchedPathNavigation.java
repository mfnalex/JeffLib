package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import net.minecraft.world.entity.ai.navigation.PathNavigation;

public class HatchedPathNavigation implements com.jeff_media.jefflib.ai.PathNavigation {

    private final PathNavigation navigation;

    public HatchedPathNavigation(PathNavigation navigation) {
        this.navigation = navigation;
    }

    @Override
    public void stop() {
        navigation.stop();
    }

    @Override
    public boolean moveTo(double x, double y, double z, double speedModifier) {
        return navigation.moveTo(x,y,z,speedModifier);
    }
}
