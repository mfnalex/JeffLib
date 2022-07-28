package com.jeff_media.jefflib.internal.nms.v1_16_R1.ai;

import com.jeff_media.jefflib.ai.PathNavigation;
import com.jeff_media.jefflib.data.BlockPos;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.nms.v1_16_R1.NMS;
import net.minecraft.server.v1_16_R1.NavigationAbstract;

public class HatchedPathNavigation implements PathNavigation {

    private final NavigationAbstract navigation;

    public HatchedPathNavigation(NavigationAbstract navigation) {
        this.navigation = navigation;
    }

    @Override
    public void stop() {
        navigation.o();
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return navigation.a(NMS.toNms(pos));
    }

    @Override
    public void setCanFloat(boolean canFloat) {
        navigation.d(canFloat);
    }

    @Override
    public boolean shouldRecomputePath(BlockPos pos) {
        throw new NMSNotSupportedException("PathNavigation.shouldRecomputePath() is not supported below 1.18");
    }

    @Override
    public float getMaxDistanceToWaypoint() {
        throw new NMSNotSupportedException("PathNavigation.getMaxDistanceToWaypoint() is not supported below 1.17");
    }

    @Override
    public boolean isStuck() {
        throw new NMSNotSupportedException("PathNavigation.isStuck() is not supported below 1.16.2");
    }

    @Override
    public boolean moveTo(double x, double y, double z, double speedModifier) {
        return navigation.a(x,y,z,speedModifier);
    }

    @Override
    public BlockPos getTargetPos() {
        return NMS.toJeff(navigation.h());
    }

    @Override
    public void setSpeedModifier(double speedModifier) {
        navigation.a(speedModifier);
    }

    @Override
    public void recomputePath() {
        navigation.j();
    }

    @Override
    public boolean isDone() {
        return navigation.m();
    }

    @Override
    public boolean isInProgress() {
        return navigation.n();
    }
}
