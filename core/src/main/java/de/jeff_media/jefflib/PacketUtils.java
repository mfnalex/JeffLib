package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.function.Predicate;

/**
 * Packet sending related methods
 */
@UtilityClass
public class PacketUtils {

    /**
     * Sends the given packet objects to a player
     */
    public static void sendPacket(@Nonnull final Player player, @Nonnull final Object... packets) {
        for(Object packet : packets) {
            JeffLib.getNMSHandler().sendPacket(player, packet);
        }
    }

    /**
     * Sends the given packet objects to all players matching the given Predicate
     */
    public static void sendPacket(@Nonnull final Predicate<Player> players, @Nonnull final Object... packets) {
        Bukkit.getOnlinePlayers().stream().filter(players).forEach(player -> sendPacket(player, packets));
    }
}
