package com.jeff_media.jefflib.internal.nms.v1_16_R2;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_16_R2.Packet;
import net.minecraft.server.v1_16_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
class NMSPacketUtils {

    private static PlayerConnection getPlayerConnection(final Player player) {
        return ((CraftPlayer)player).getHandle().playerConnection;
    }

    public static void sendPacket(final Player player, final Object packet) {
        if(!(packet instanceof Packet<?>)) {
            throw new IllegalArgumentException(packet + " is not instanceof " + Packet.class.getName());
        }
        getPlayerConnection(player).sendPacket((Packet<?>) packet);
    }

}
