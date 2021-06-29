package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.InvalidLocationDefinitionException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LocationUtils {

    /**
     * Gets a Location from a ConfigurationSection.
     *
     * The values "x", "y" and "z" must be defined.
     * If no world is given, "world" must be defined as well (the world's name, not UID)
     * "pitch" and "yaw" can be defined, but if so, BOTH have to be defined.
     *
     * @param world
     * @return
     */
    @NotNull
    public static Location getLocationFromSection(ConfigurationSection config, @Nullable World world) throws InvalidLocationDefinitionException {
        if(!config.isSet("x") || (!config.isDouble("x") && !config.isInt("x"))) {
            throw new InvalidLocationDefinitionException("\"x\" coordinate not defined or not a number");
        }
        double x = config.getDouble("x");

        if(!config.isSet("y") || (!config.isDouble("y") && !config.isInt("y"))) {
            throw new InvalidLocationDefinitionException("\"y\" coordinate not defined or not a number");
        }
        double y = config.getDouble("y");

        if(!config.isSet("z") || (!config.isDouble("z") && !config.isInt("z"))) {
            throw new InvalidLocationDefinitionException("\"z\" coordinate not defined or not a number");
        }
        double z = config.getDouble("z");

        if(config.isSet("world")) {
            String worldName = config.getString("world");
            assert worldName != null;
            if(Bukkit.getWorld(worldName) != null) {
                world = Bukkit.getWorld(worldName);
            } else {
                try {
                    UUID uuid = UUID.fromString(worldName);
                    world = Bukkit.getWorld(uuid);
                } catch (IllegalArgumentException ignored) {

                }
            }
            if(world == null) {
                throw new InvalidLocationDefinitionException("World \""+worldName+"\" not found.");
            }
        }
        if(world == null) {
            throw new InvalidLocationDefinitionException("\"world\" not defined");
        }
        if(!config.isSet("pitch") && !config.isSet("yaw")) {
            return new Location(world,x,y,z);
        }
        if(config.isSet("pitch") && !config.isSet("yaw")) {
            throw new InvalidLocationDefinitionException("When \"pitch\" is defined, \"yaw\" must also be defined");
        }
        if(!config.isSet("pitch") && config.isSet("yaw")) {
            throw new InvalidLocationDefinitionException("When \"yaw\" is defined, \"pitch\" must also be defined");
        }
        if((!config.isDouble("pitch") && !config.isInt("pitch"))) {
            throw new InvalidLocationDefinitionException("\"pitch\" value is not a valid number");
        }
        double pitch = config.getDouble("pitch");

        if((!config.isDouble("yaw") && !config.isInt("yaw"))) {
            throw new InvalidLocationDefinitionException("\"yaw\" value is not a valid number");
        }
        double yaw = config.getDouble("yaw");

        return new Location(world,x,y,z,(float)yaw,(float)pitch);
    }

    public static ChunkCoordinates getChunkCoordinates(Location location) {
        int cx = location.getBlockX() >> 4;
        int cz = location.getBlockZ() >> 4;
        return new ChunkCoordinates(cx,cz);
    }

    public static ChunkCoordinates getChunkCoordinates(int x, int z) {
        return new ChunkCoordinates(x >> 4, z >> 4);
    }

    public static boolean isChunkGenerated(Location location) {
        ChunkCoordinates chunkCoordinates = getChunkCoordinates(location);
        int cx = chunkCoordinates.getX();
        int cz = chunkCoordinates.getZ();
        return location.getWorld().isChunkGenerated(cx,cz);
    }

    public static boolean isChunkLoaded(Location location) {
        ChunkCoordinates chunkCoordinates = getChunkCoordinates(location);
        int cx = chunkCoordinates.getX();
        int cz = chunkCoordinates.getZ();
        if(!location.getWorld().isChunkGenerated(cx,cz)) return false;
        return location.getWorld().isChunkLoaded(cx, cz);
    }

    public static class ChunkCoordinates {
        @Override
        public String toString() {
            return "ChunkCoordinates{" +
                    "x=" + x +
                    ", z=" + z +
                    '}';
        }

        private final int x, z;

        public ChunkCoordinates(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
    }

}
