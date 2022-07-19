package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.InvalidLocationDefinitionException;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Location related methods
 */
@UtilityClass
public final class LocationUtils {

    /**
     * Converts a location to a Vector containing the location's coordinates inside the chunk (rounded to int values)
     *
     * @param location Location
     * @return Vector containing the X, Y and Z int values of the location inside its chunk
     */
    public static Vector getCoordinatesInsideChunk(final Location location) {
        final int x = location.getBlockX() & 0x000F;
        final int y = location.getBlockY();
        final int z = location.getBlockZ() & 0x000F;
        return new Vector(x, y, z);
    }

    /**
     * Gets a Location from a ConfigurationSection.
     * <p>
     * The values "x", "y" and "z" must be defined, or "spawn" must be set to true.
     * If no world is given, "world" must be defined as well (the world's name, not UID)
     * "pitch" and "yaw" can be defined, but if so, BOTH have to be defined.
     *
     * @param world Default world to use when to world is specified in the ConfigurationSection
     * @return Location parsed from the given configuration section
     */
    @Nonnull
    public static Location getLocationFromSection(final ConfigurationSection config, @Nullable World world) {
        if (config.getBoolean("spawn")) {
            final World world2 = Bukkit.getWorld(Objects.requireNonNull(config.getString("world")));
            if (world2 == null) {
                throw new InvalidLocationDefinitionException("World \"" + world + "\" is not found.");
            }
            return world2.getSpawnLocation();
        }
        if (!config.isSet("x") || (!config.isDouble("x") && !config.isInt("x"))) {
            throw new InvalidLocationDefinitionException("\"x\" coordinate not defined or not a number");
        }
        final double x = config.getDouble("x");

        if (!config.isSet("y") || (!config.isDouble("y") && !config.isInt("y"))) {
            throw new InvalidLocationDefinitionException("\"y\" coordinate not defined or not a number");
        }
        final double y = config.getDouble("y");

        if (!config.isSet("z") || (!config.isDouble("z") && !config.isInt("z"))) {
            throw new InvalidLocationDefinitionException("\"z\" coordinate not defined or not a number");
        }
        final double z = config.getDouble("z");

        if (config.isSet("world")) {
            final String worldName = config.getString("world");
            assert worldName != null;
            if (Bukkit.getWorld(worldName) != null) {
                world = Bukkit.getWorld(worldName);
            } else {
                try {
                    final UUID uuid = UUID.fromString(worldName);
                    world = Bukkit.getWorld(uuid);
                } catch (final IllegalArgumentException ignored) {

                }
            }
            if (world == null) {
                throw new InvalidLocationDefinitionException("World \"" + worldName + "\" not found.");
            }
        }
        if (world == null) {
            throw new InvalidLocationDefinitionException("\"world\" not defined");
        }
        if (!config.isSet("pitch") && !config.isSet("yaw")) {
            return new Location(world, x, y, z);
        }
        if (config.isSet("pitch") && !config.isSet("yaw")) {
            throw new InvalidLocationDefinitionException("When \"pitch\" is defined, \"yaw\" must also be defined");
        }
        if (!config.isSet("pitch") && config.isSet("yaw")) {
            throw new InvalidLocationDefinitionException("When \"yaw\" is defined, \"pitch\" must also be defined");
        }
        if ((!config.isDouble("pitch") && !config.isInt("pitch"))) {
            throw new InvalidLocationDefinitionException("\"pitch\" value is not a valid number");
        }
        final double pitch = config.getDouble("pitch");

        if ((!config.isDouble("yaw") && !config.isInt("yaw"))) {
            throw new InvalidLocationDefinitionException("\"yaw\" value is not a valid number");
        }
        final double yaw = config.getDouble("yaw");

        return new Location(world, x, y, z, (float) yaw, (float) pitch);
    }

    /**
     * Returns a {@link ChunkCoordinates} object for the given location
     *
     * @param location Location
     * @return ChunkCoordinates for the given Location
     */
    public static ChunkCoordinates getChunkCoordinates(final Location location) {
        final int cx = location.getBlockX() >> 4;
        final int cz = location.getBlockZ() >> 4;
        return new ChunkCoordinates(cx, cz);
    }

    /**
     * Returns a {@link ChunkCoordinates} object for the given x and z coordinates
     *
     * @param x X coordinate
     * @param z Z coordinate
     * @return ChunkCoordinates for the given Location
     */
    public static ChunkCoordinates getChunkCoordinates(final int x, final int z) {
        return new ChunkCoordinates(x >> 4, z >> 4);
    }

    /**
     * Returns whether a chunk at a given location has been generated (without generating it)
     *
     * @param location Location
     * @return true when the chunk has been generated, otherwise false
     */
    public static boolean isChunkGenerated(final Location location) {
        final ChunkCoordinates chunkCoordinates = getChunkCoordinates(location);
        final int cx = chunkCoordinates.getX();
        final int cz = chunkCoordinates.getZ();
        return Objects.requireNonNull(location.getWorld()).isChunkGenerated(cx, cz);
    }

    /**
     * Returns whether a chunk at a given location is loaded (without loading it)
     *
     * @param location Location
     * @return true when the chunk is loaded, otherwise false
     */
    public static boolean isChunkLoaded(final Location location) {
        final ChunkCoordinates chunkCoordinates = getChunkCoordinates(location);
        final int cx = chunkCoordinates.getX();
        final int cz = chunkCoordinates.getZ();
        if (!Objects.requireNonNull(location.getWorld()).isChunkGenerated(cx, cz)) return false;
        return location.getWorld().isChunkLoaded(cx, cz);
    }

    /**
     * Turns a location into a human readable String, optionally including yaw/pitch and the world. Example: x=10, y=64, z=-10 or x=10, y=64, z=-10, yaw=0, pitch=0, world=world_the_end
     *
     * @param loc             Location
     * @param showYawAndPitch Whether to include Yaw and Pitch
     * @param showWorld       Whether to include the world's name
     * @return Human readable location string
     */
    public static String toPrettyString(final Location loc, final boolean showYawAndPitch, final boolean showWorld) {
        final double x = loc.getX();
        final double y = loc.getY();
        final double z = loc.getZ();
        final double yaw = loc.getYaw();
        final double pitch = loc.getPitch();
        final String world = Objects.requireNonNull(loc.getWorld()).getName();
        String result = String.format(Locale.ROOT, "x=%.2f, y=%.2f, z=%.2f", x, y, z);
        if (showYawAndPitch) {
            result += String.format(", yaw=%.2f, pitch=%.2f", yaw, pitch);
        }
        if (showWorld) {
            result += ", world=" + world;
        }
        return result;
    }

    /**
     * Represents a pair of X,Z chunk coordinates
     */
    public static final class ChunkCoordinates {
        private final int x, z;

        public ChunkCoordinates(final int x, final int z) {
            this.x = x;
            this.z = z;
        }

        @Override
        public String toString() {
            return "ChunkCoordinates{" +
                    "x=" + x +
                    ", z=" + z +
                    '}';
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
    }

}
