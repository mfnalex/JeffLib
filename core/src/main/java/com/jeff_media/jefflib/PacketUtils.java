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

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

/**
 * Packet sending related methods
 */
@UtilityClass
public class PacketUtils {

    /**
     * Sends the given packet objects to all players matching the given Predicate
     */
    public static void sendPacket(@Nonnull final Predicate<Player> players, @Nonnull final Object... packets) {
        Bukkit.getOnlinePlayers().stream().filter(players).forEach(player -> sendPacket(player, packets));
    }

    /**
     * Sends the given packet objects to a player
     */
    public static void sendPacket(@Nonnull final Player player, @Nonnull final Object... packets) {
        for (Object packet : packets) {
            JeffLib.getNMSHandler().sendPacket(player, packet);
        }
    }
}
