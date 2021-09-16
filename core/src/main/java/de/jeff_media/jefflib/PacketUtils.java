package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class PacketUtils {

    public static void sendPacket(@NotNull final Player player, @NotNull final Object packet) {
        NMSNotSupportedException.check();
        JeffLib.getNMSHandler().sendPacket(player, packet);
    }
}
