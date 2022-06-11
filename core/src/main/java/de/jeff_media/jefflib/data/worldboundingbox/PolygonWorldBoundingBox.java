package de.jeff_media.jefflib.data.worldboundingbox;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PolygonWorldBoundingBox extends WorldBoundingBox {

    @Getter
    @Setter
    @Nonnull
    private World world;

    @Nonnull
    @Getter
    private final List<Vector> points;

    private final double minY;
    private final double maxY;

    private PolygonWorldBoundingBox(final @Nonnull World world, final @Nonnull List<Vector> points) {
        this.world = world;
        this.points = Collections.unmodifiableList(points);
        this.minY = points.stream().mapToDouble(Vector::getY).min().getAsDouble();
        this.maxY = points.stream().mapToDouble(Vector::getY).max().getAsDouble();
    }

    public static PolygonWorldBoundingBox fromLocations(final @Nonnull World world, final @Nonnull List<Location> points) {
        return new PolygonWorldBoundingBox(world, points.stream().map(Location::toVector).collect(Collectors.toList()));
    }

    public static PolygonWorldBoundingBox fromLocations(final @Nonnull World world, final @Nonnull Location... points) {
        return fromLocations(world, Arrays.asList(points));
    }

    public static PolygonWorldBoundingBox fromVectors(final @Nonnull World world, final @Nonnull Vector... points) {
        return fromVectors(world, Arrays.asList(points));
    }

    public static PolygonWorldBoundingBox fromVectors(final @Nonnull World world, final @Nonnull List<Vector> points) {
        return new PolygonWorldBoundingBox(world, points);
    }

    public boolean contains(Location location) {
        if (points.size() < 3) {
            return false;
        }
        int targetX = location.getBlockX(); //wide
        int targetY = location.getBlockY(); //height
        int targetZ = location.getBlockZ(); //depth

        if (targetY < minY || targetY > maxY) {
            return false;
        }

        boolean inside = false;
        int npoints = points.size();
        int xNew;
        int zNew;
        int x1;
        int z1;
        int x2;
        int z2;
        long crossproduct;
        int i;

        int xOld = points.get(npoints - 1).getBlockX();
        int zOld = points.get(npoints - 1).getBlockZ();

        for (i = 0; i < npoints; ++i) {
            xNew = points.get(i).getBlockX();
            zNew = points.get(i).getBlockZ();
            //Check for corner
            if (xNew == targetX && zNew == targetZ) {
                return true;
            }
            if (xNew > xOld) {
                x1 = xOld;
                x2 = xNew;
                z1 = zOld;
                z2 = zNew;
            } else {
                x1 = xNew;
                x2 = xOld;
                z1 = zNew;
                z2 = zOld;
            }
            if (x1 <= targetX && targetX <= x2) {
                crossproduct = ((long) targetZ - (long) z1) * (long) (x2 - x1)
                        - ((long) z2 - (long) z1) * (long) (targetX - x1);
                if (crossproduct == 0) {
                    if ((z1 <= targetZ) == (targetZ <= z2)) {
                        return true; //on edge
                    }
                } else if (crossproduct < 0 && (x1 != targetX)) {
                    inside = !inside;
                }
            }
            xOld = xNew;
            zOld = zNew;
        }

        return inside;
    }

    @Override
    public @Nonnull World getWorld() {
        return null;
    }

}
