package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

@UtilityClass
public class PacketUtils {

    /**
     * Sends the given RequiresNMS packet object to the player.
     */
    public static void sendPacket(@Nonnull final Player player, @Nonnull final Object packet) {
        JeffLib.getNMSHandler().sendPacket(player, packet);
    }
}
