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

package com.jeff_media.jefflib;

import java.util.Objects;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

/**
 * Particle related methods
 */
@UtilityClass
public class ParticleUtils {

    public static final Particle PARTICLE_EXPLOSION_NORMAL = EnumUtils.getIfPresent(Particle.class, "EXPLOSION_NORMAL").orElseGet(() -> EnumUtils.getIfPresent(Particle.class, "EXPLOSION").orElse(null));

    /**
     * Returns a runnable that creates cube-formed particles
     *
     * @param min           first corner
     * @param max           second corner
     * @param player        player to show the particles to
     * @param particleType  particle type
     * @param particleCount particle count
     * @return Runnable that shows the particles to the player
     */
    public static BukkitRunnable drawHollowCube(final Block min, final Block max, final Player player, final Particle particleType, final int particleCount, @Nullable final Object data) {
        final World world = min.getWorld();
        if (!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world");
        }
        return drawHollowCube(world, BoundingBox.of(min, max), player, particleType, particleCount, data);
    }

    /**
     * Returns a runnable that creates cube-formed particles
     *
     * @param world         world
     * @param boundingBox   bounding box
     * @param player        player to show the particles to
     * @param particleType  particle type
     * @param particleCount particle count
     * @return Runnable that shows the particles to the player
     */
    public static BukkitRunnable drawHollowCube(final World world, final BoundingBox boundingBox, final Player player, final Particle particleType, final int particleCount, @Nullable final Object data) {
        final Set<Location> points = GeometryUtils.getHollowCube(boundingBox.getMin().toLocation(world), boundingBox.getMax().toLocation(world), 0.5);
        return new BukkitRunnable() {
            int count;

            @Override
            public void run() {
                for (final Location location : points) {
                    if (data == null) {
                        player.spawnParticle(particleType, location, particleCount);
                    } else {
                        player.spawnParticle(particleType, location, particleCount, data);
                    }
                }
                count++;
                if (count >= 4) cancel();
            }
        };
    }

    /**
     * Returns a runnable that creates cube-formed particles
     *
     * @param min           first corner
     * @param max           second corner
     * @param player        player to show the particles to
     * @param particleType  particle type
     * @param particleCount particle count
     * @return Runnable that shows the particles to the player
     */
    public static BukkitRunnable drawHollowCube(final Location min, final Location max, final Player player, final Particle particleType, final int particleCount, @Nullable final Object data) {
        final World world = min.getWorld();
        if (!Objects.requireNonNull(world).equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world");
        }
        return drawHollowCube(world, BoundingBox.of(min, max), player, particleType, particleCount, data);
    }


}
