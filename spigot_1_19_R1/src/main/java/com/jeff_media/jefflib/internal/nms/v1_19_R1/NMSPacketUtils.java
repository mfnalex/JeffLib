package com.jeff_media.jefflib.internal.nms.v1_19_R1;

import lombok.experimental.UtilityClass;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
class NMSPacketUtils {

    private static Connection getPlayerConnection(final Player player) {
        return ((CraftPlayer)player).getHandle().connection.connection;
    }

    public static void sendPacket(final Player player, final Object packet) {
        if(!(packet instanceof Packet<?>)) {
            throw new IllegalArgumentException(packet + " is not instanceof " + Packet.class.getName());
        }
        getPlayerConnection(player).send((Packet<?>) packet);
    }
}
