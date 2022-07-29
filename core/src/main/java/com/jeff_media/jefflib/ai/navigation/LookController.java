package com.jeff_media.jefflib.ai.navigation;

import com.jeff_media.jefflib.internal.annotations.NMS;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public interface LookController {

    @NMS
    void setLookAt(Vector destination);

    @NMS("1.17")
    void setLookAt(Entity destination);

    @NMS
    void setLookAt(Entity destination, float yMaxRotSpeed, float xMaxRotAngle);

    @NMS
    void setLookAt(double x, double y, double z);

    @NMS
    void setLookAt(double x, double y, double z, float yMaxRotSpeed, float xMaxRotAngle);

    @NMS("1.18")
    boolean isLookingAtTarget();

    @NMS
    double getWantedX();

    @NMS
    double getWantedY();

    @NMS
    double getWantedZ();


}
