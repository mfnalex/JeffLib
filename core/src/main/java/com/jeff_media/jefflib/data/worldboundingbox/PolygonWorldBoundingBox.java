/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.data.worldboundingbox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Represents a polygon region linked to a {@link World}
 */
public final class PolygonWorldBoundingBox extends WorldBoundingBox {

    @Nonnull
    @Getter
    private final List<Vector> points;
    private final double minY;
    private final double maxY;
    @Setter
    @Nonnull
    private World world;

    private PolygonWorldBoundingBox(@Nonnull final World world, @Nonnull final List<Vector> points) {
        this.world = world;
        this.points = Collections.unmodifiableList(points);
        this.minY = points.stream().mapToDouble(Vector::getY).min().getAsDouble();
        this.maxY = points.stream().mapToDouble(Vector::getY).max().getAsDouble();
    }

    public static PolygonWorldBoundingBox fromLocations(@Nonnull final World world, @Nonnull final Location... points) {
        return fromLocations(world, Arrays.asList(points));
    }

    public static PolygonWorldBoundingBox fromLocations(@Nonnull final World world, @Nonnull final List<Location> points) {
        return new PolygonWorldBoundingBox(world, points.stream().map(Location::toVector).collect(Collectors.toList()));
    }

    public static PolygonWorldBoundingBox fromVectors(@Nonnull final World world, @Nonnull final Vector... points) {
        return fromVectors(world, Arrays.asList(points));
    }

    public static PolygonWorldBoundingBox fromVectors(@Nonnull final World world, @Nonnull final List<Vector> points) {
        return new PolygonWorldBoundingBox(world, points);
    }

    public boolean contains(final Location location) {
        if (points.size() < 3) {
            return false;
        }
        final int targetX = location.getBlockX(); //wide
        final int targetY = location.getBlockY(); //height
        final int targetZ = location.getBlockZ(); //depth

        if (targetY < minY || targetY > maxY) {
            return false;
        }

        boolean inside = false;
        final int npoints = points.size();
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
                crossproduct = ((long) targetZ - (long) z1) * (long) (x2 - x1) - ((long) z2 - (long) z1) * (long) (targetX - x1);
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
    @Nonnull
    public World getWorld() {
        return null;
    }

}
