package de.jeff_media.jefflib.data.worldboundingbox;

import de.jeff_media.jefflib.LocationUtils;
import de.jeff_media.jefflib.exceptions.InvalidRegionDefinitionException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class WorldBoundingBox implements ConfigurationSerializable {

    @Nonnull abstract World getWorld();

    abstract boolean contains(Location location);

    abstract List<Vector> getPoints();

    static WorldBoundingBox fromConfigurationSection(@Nonnull final ConfigurationSection section, @Nullable final String path) {

        final String worldPath = path == null ? "world" : path + ".world";
        final String pointsPath = path == null ? "points" : path + ".points";


        if(path != null && !section.isConfigurationSection(path)) {
            throw new InvalidRegionDefinitionException("No valid region definition found at given path.");
        }

        if(!section.isString(worldPath)) throw new InvalidRegionDefinitionException("Region definitions must contain a world.");
        final World world = Bukkit.getWorld(section.getString(worldPath));
        if(world == null) throw new InvalidRegionDefinitionException("World " + section.getString(worldPath) + " not found.");

        if(section.isList(pointsPath)) {
            return getUnknownWorldBoundingBoxFromConfigurationSection(section, section.getList(pointsPath), world);
        }

        return getCuboidWorldBoundingBoxFromConfigurationSection(section, path, world);
    }

    @Nonnull
    private static WorldBoundingBox getUnknownWorldBoundingBoxFromConfigurationSection(@Nonnull final ConfigurationSection section, @Nonnull final List<?> points, @Nonnull final World world) {
        final List<Vector> vectors = new ArrayList<>();
        for(final Object point : points) {
            if(!(point instanceof Vector)) {
                throw new InvalidRegionDefinitionException("Points list contains an object not instanceof org.bukkit.util.Vector");
            }
            vectors.add((Vector) point);
        }
        if(vectors.size()<2) {
            throw new InvalidRegionDefinitionException("Region definitions must at least contain two points.");
        }
        if(vectors.size()==2) {
            return new CuboidWorldBoundingBox(world, BoundingBox.of(vectors.get(0),vectors.get(1)));
        }
        return PolygonWorldBoundingBox.fromVectors(world, vectors);
    }

    @Nonnull
    private static CuboidWorldBoundingBox getCuboidWorldBoundingBoxFromConfigurationSection(@Nonnull final ConfigurationSection section, @Nullable final String path, @Nonnull final World world) {
        final String minPath = path == null ? "min" : path + ".min";
        final String maxPath = path == null ? "max" : path + ".max";

        if (!section.contains(minPath) || !section.isConfigurationSection(minPath)
                || !section.contains(maxPath) || !section.isConfigurationSection(maxPath)) {
            throw new InvalidRegionDefinitionException("Invalid region defined. Cuboid regions must contain a min and max position.");
        }

        final Location min = LocationUtils.getLocationFromSection(section.getConfigurationSection(minPath), world);
        final Location max = LocationUtils.getLocationFromSection(section.getConfigurationSection(maxPath), world);
        return new CuboidWorldBoundingBox(world, BoundingBox.of(min,max));
    }

    @Nonnull
    @Override
    public Map<String, Object> serialize() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("world",getWorld());
        final List<Vector> points = new ArrayList<>(getPoints());
        return map;
    }
}
