package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

@UtilityClass
public final class VectorUtils {

    /**
     * Returns a location at the same coordinates as origin, looking exactly towards the given destination
     * @param origin        Origin location
     * @param destination   Location to look at
     * @return              Origin location with adjusted yaw and pitch to look exactly towards the given destination
     */
    public static Location lookAt(final Location origin, final Location destination) {
        final Location location = origin.clone();

        final double dx = destination.getX() - location.getX();
        final double dy = destination.getY() - location.getY();
        final double dz = destination.getZ() - location.getZ();

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

        final double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        location.setPitch((float) -Math.atan(dy / dxz));

        location.setYaw(-location.getYaw() * 180f / (float) Math.PI);
        location.setPitch(location.getPitch() * 180f / (float) Math.PI);

        return location;
    }

    private static EulerAngle convertVectorToEulerAngle(Vector vec) {

        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();

        double xz = Math.sqrt(x*x + z*z);

        double eulX;
        if(x < 0) {
            if(y == 0) {
                eulX = Math.PI*0.5;
            } else {
                eulX = Math.atan(xz/y)+Math.PI;
            }
        } else {
            eulX = Math.atan(y/xz)+Math.PI*0.5;
        }

        double eulY;
        if(x == 0) {
            if(z > 0) {
                eulY = Math.PI;
            } else {
                eulY = 0;
            }
        } else {
            eulY = Math.atan(z/x)+Math.PI*0.5;
        }

        return new EulerAngle(eulX, eulY, 0);

    }

    public static Vector getCenter(Vector vector1, Vector vector2) {
        double x1 = vector1.getX();
        double y1 = vector1.getY();
        double z1 = vector1.getZ();
        double x2 = vector2.getX();
        double y2 = vector2.getY();
        double z2 = vector2.getZ();
        return new Vector(x1+x2/2, y1+y2/2, z1+z2/2);
    }

}
