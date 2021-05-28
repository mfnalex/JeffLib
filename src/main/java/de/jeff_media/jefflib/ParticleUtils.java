package de.jeff_media.jefflib;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.HashSet;
import java.util.Set;

public class ParticleUtils {

    public static BukkitRunnable drawHollowCube(Block min, Block max, Player player, Particle particleType, int particleCount) {
        World world = min.getWorld();
        if(!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world");
        }
        return drawHollowCube(world, BoundingBox.of(min, max), player, particleType, particleCount);
    }

    public static BukkitRunnable drawHollowCube(Location min, Location max, Player player, Particle particleType, int particleCount) {
        World world = min.getWorld();
        if(!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world");
        }
        return drawHollowCube(world, BoundingBox.of(min, max), player, particleType, particleCount);
    }

    public static BukkitRunnable drawHollowCube(World world, BoundingBox boundingBox, Player player, Particle particleType, int particleCount) {
        Set<Location> points = GeometryUtils.getHollowCube(boundingBox.getMin().toLocation(world), boundingBox.getMax().toLocation(world), 0.5);
        return new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                for(Location location : points) {
                    player.spawnParticle(particleType, location, particleCount);
                }
                count++;
                if(count >= 4) cancel();
            }
        };
    }


}
