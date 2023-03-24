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

package com.jeff_media.jefflib.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Represents a {@link org.bukkit.Location} like object. Unlike a normal Location, coordinates can only be integers.
 * Yaw and Pitch don't exist at all. This can be used to roughly compare Locations.
 */
@Data
@AllArgsConstructor
public class SimpleLocation {
    private World world;
    private int x;
    private int y;
    private int z;

    /**
     * Creates a SimpleLocation from a given {@link Location}
     */
    public SimpleLocation(final Location location) {
        this(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
