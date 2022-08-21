/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.internal.nms.v1_16_R1;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
public class NMSPacketUtils {

    public static void sendPacket(final Player player, final Object packet) {
        if (!(packet instanceof Packet<?>)) {
            throw new IllegalArgumentException(packet + " is not instanceof " + Packet.class.getName());
        }
        getPlayerConnection(player).sendPacket((Packet<?>) packet);
    }

    private static PlayerConnection getPlayerConnection(final Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

}
