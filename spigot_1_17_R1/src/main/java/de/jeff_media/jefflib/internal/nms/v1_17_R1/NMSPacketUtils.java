package de.jeff_media.jefflib.internal.nms.v1_17_R1;

import lombok.experimental.UtilityClass;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
class NMSPacketUtils {

    private static PlayerConnection getPlayerConnection(final Player player) {
        return ((CraftPlayer)player).getHandle().b;
    }

    public static void sendPacket(final Player player, final Object packet) {
        if(!(packet instanceof Packet<?>)) {
            throw new IllegalArgumentException(packet + " is not instanceof " + Packet.class.getName());
        }
        getPlayerConnection(player).sendPacket((Packet<?>) packet);
    }
}
