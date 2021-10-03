package de.jeff_media.jefflib.internal.nms.v1_16_R1;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
public class NMSPacketUtils {

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
