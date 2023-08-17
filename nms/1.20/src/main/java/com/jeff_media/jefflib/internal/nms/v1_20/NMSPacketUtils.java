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

package com.jeff_media.jefflib.internal.nms.v1_20;

import lombok.experimental.UtilityClass;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
class NMSPacketUtils {

    public static void sendPacket(final Player player, final Object packet) {
        if (!(packet instanceof Packet<?>)) {
            throw new IllegalArgumentException(packet + " is not instanceof " + Packet.class.getName());
        }
        getPlayerConnection(player).send((Packet<?>) packet);
    }

    public static void sendPacket(final ServerPlayer player, final Packet<?> packet) {
        player.connection.send(packet);
    }

    private static ServerGamePacketListenerImpl getPlayerConnection(final Player player) {
        return ((CraftPlayer) player).getHandle().connection;
    }
}
