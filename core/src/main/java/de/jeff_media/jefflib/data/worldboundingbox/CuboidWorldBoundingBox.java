package de.jeff_media.jefflib.data.worldboundingbox;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a {@link BoundingBox} linked to a {@link World}
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

    public CuboidWorldBoundingBox(final @NotNull World world, final @NotNull BoundingBox boundingBox) {
        this.world = world;
        this.boundingBox = boundingBox;
    }

    public boolean contains(Location location) {
        return boundingBox.contains(location.toVector());
    }

    @Override
    List<Vector> getPoints() {
        return Arrays.asList(boundingBox.getMin(), boundingBox.getMax());
    }

    @Override
    public String toString() {
        return "CuboidWorldBoundingBox{" +
                "world=" + world +
                ", boundingBox=" + boundingBox +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CuboidWorldBoundingBox that = (CuboidWorldBoundingBox) o;
        return world.equals(that.world) && boundingBox.equals(that.boundingBox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, boundingBox);
    }
}
