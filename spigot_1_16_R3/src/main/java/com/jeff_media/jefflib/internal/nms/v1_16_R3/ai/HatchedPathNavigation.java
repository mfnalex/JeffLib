package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.nms.v1_16_R3.NMS;
import net.minecraft.server.v1_16_R3.NavigationAbstract;
import org.bukkit.util.BlockVector;

public class HatchedPathNavigation implements com.jeff_media.jefflib.ai.PathNavigation {

    private final NavigationAbstract navigation;

    public HatchedPathNavigation(final NavigationAbstract navigation) {
        this.navigation = navigation;
    }

    @Override
    public void stop() {
        navigation.o();
    }

    @Override
    public boolean isStableDestination(final BlockVector pos) {
        return navigation.a(NMS.toNms(pos));
    }

    @Override
    public void setCanFloat(final boolean canFloat) {
        navigation.d(canFloat);
    }

    @Override
    public boolean shouldRecomputePath(final BlockVector pos) {
        throw new NMSNotSupportedException("1_17_R1 and older does not support this method");
    }

    @Override
    public float getMaxDistanceToWaypoint() {
        throw new NMSNotSupportedException("1_16_R3 and older does not support this method");
    }

    @Override
    public boolean isStuck() {
        return navigation.t();
    }

    @Override
    public boolean moveTo(final double x, final double y, final double z, final double speedModifier) {
        return navigation.a(x,y,z,speedModifier);
    }

    @Override
    public BlockVector getTargetPos() {
        return NMS.toBukkit(navigation.h());
    }

    @Override
    public void setSpeedModifier(final double speedModifier) {
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
