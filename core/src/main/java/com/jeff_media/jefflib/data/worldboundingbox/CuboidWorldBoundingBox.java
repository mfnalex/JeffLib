/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.data.worldboundingbox;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a cuboid region linked to a {@link World}
 */
public class CuboidWorldBoundingBox extends WorldBoundingBox {
    @Getter
    @Setter
    @NotNull
    private World world;

    @Getter
    @Setter
    @NotNull
    private BoundingBox boundingBox;

    public CuboidWorldBoundingBox(@NotNull final World world, @NotNull final BoundingBox boundingBox) {
        this.world = world;
        this.boundingBox = boundingBox;
    }

    public boolean contains(final Location location) {
        return boundingBox.contains(location.toVector());
    }

    @Override
    List<Vector> getPoints() {
        return Arrays.asList(boundingBox.getMin(), boundingBox.getMax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, boundingBox);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CuboidWorldBoundingBox that = (CuboidWorldBoundingBox) o;
        return world.equals(that.world) && boundingBox.equals(that.boundingBox);
    }

    @Override
    public String toString() {
        return "CuboidWorldBoundingBox{" + "world=" + world + ", boundingBox=" + boundingBox + '}';
    }
}
