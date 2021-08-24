package de.jeff_media.jefflib.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WorldBoundingBox {
    @Getter
    @Setter
    @NotNull
    private World world;

    @Getter
    @Setter
    @NotNull
    private BoundingBox boundingBox;

    @Override
    public String toString() {
        return "WorldBoundingBox{" +
                "world=" + world +
                ", boundingBox=" + boundingBox +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldBoundingBox that = (WorldBoundingBox) o;
        return world.equals(that.world) && boundingBox.equals(that.boundingBox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, boundingBox);
    }

    public WorldBoundingBox(final @NotNull World world, final @NotNull BoundingBox boundingBox) {
        this.world = world;
        this.boundingBox = boundingBox;
    }
}
