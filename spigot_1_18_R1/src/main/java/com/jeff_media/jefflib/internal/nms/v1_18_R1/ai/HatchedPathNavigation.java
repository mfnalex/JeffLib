package com.jeff_media.jefflib.internal.nms.v1_18_R1.ai;

import com.jeff_media.jefflib.internal.nms.v1_18_R1.NMS;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.bukkit.util.BlockVector;

public class HatchedPathNavigation implements com.jeff_media.jefflib.ai.PathNavigation {

    private final PathNavigation navigation;

    public HatchedPathNavigation(final PathNavigation navigation) {
        this.navigation = navigation;
    }

    @Override
    public boolean moveTo(final double x, final double y, final double z, final double speedModifier) {
        return navigation.moveTo(x, y, z, speedModifier);
    }

    @Override
    public BlockVector getTargetPos() {
        return NMS.toBukkit(navigation.getTargetPos());
    }

    @Override
    public void setSpeedModifier(final double speedModifier) {
        navigation.setSpeedModifier(speedModifier);
    }

    @Override
    public void recomputePath() {
        navigation.recomputePath();
    }

    @Override
    public boolean isDone() {
        return navigation.isDone();
    }

    @Override
    public boolean isInProgress() {
        return navigation.isInProgress();
    }

    @Override
    public void stop() {
        navigation.stop();
    }

    @Override
    public boolean isStableDestination(final BlockVector pos) {
        return navigation.isStableDestination(NMS.toNms(pos));
    }

    @Override
    public void setCanFloat(final boolean canFloat) {
        navigation.setCanFloat(canFloat);
    }

    @Override
    public boolean shouldRecomputePath(final BlockVector pos) {
        return navigation.shouldRecomputePath(NMS.toNms(pos));
    }

    @Override
    public float getMaxDistanceToWaypoint() {
        return navigation.getMaxDistanceToWaypoint();
    }

    @Override
    public boolean isStuck() {
        return navigation.isStuck();
    }
}
