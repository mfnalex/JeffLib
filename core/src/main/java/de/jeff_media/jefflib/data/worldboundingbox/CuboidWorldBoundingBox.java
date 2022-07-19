package de.jeff_media.jefflib.data.worldboundingbox;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cuboid region linked to a {@link World}
 */
public class CuboidWorldBoundingBox extends WorldBoundingBox {
    @Getter
    @Setter
    @Nonnull
    private World world;

    @Getter
    @Setter
    @Nonnull
    private BoundingBox boundingBox;

    public CuboidWorldBoundingBox(@Nonnull final World world, @Nonnull final BoundingBox boundingBox) {
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
