package de.jeff_media.jefflib;

import org.bukkit.Location;

public class VectorUtils {

    /**
     * Returns a location at the same coordinates as origin, looking exactly towards the given destination
     * @param origin        Origin location
     * @param destination   Location to look at
     * @return              Origin location with adjusted yaw and pitch to look exactly towards the given destination
     */
    public static Location lookAt(final Location origin, final Location destination) {
        Location location = origin.clone();

        double dx = destination.getX() - location.getX();
        double dy = destination.getY() - location.getY();
        double dz = destination.getZ() - location.getZ();

        if (dx != 0) {
            if (dx < 0) {
                location.setYaw((float) (1.5 * Math.PI));
            } else {
                location.setYaw((float) (0.5 * Math.PI));
            }
            location.setYaw(location.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            location.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        location.setPitch((float) -Math.atan(dy / dxz));

        location.setYaw(-location.getYaw() * 180f / (float) Math.PI);
        location.setPitch(location.getPitch() * 180f / (float) Math.PI);

        return location;
    }

}
