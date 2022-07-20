package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.Hologram;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages holograms
 * @deprecated Draft API
 */
@UtilityClass
@Deprecated
public class HologramManager {

    @Getter
    private static final List<Hologram> holograms = new ArrayList<>();
    private static final Map<OfflinePlayer, List<Hologram>> SHOWN_HOLOGRAMS = new HashMap<>();
    private static final Runnable RUNNABLE = () -> {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            for (final Hologram hologram : holograms) {
                if (!SHOWN_HOLOGRAMS.containsKey(player)) {
                    SHOWN_HOLOGRAMS.put(player, new ArrayList<>());
                }

                // Show hologram
                if (!SHOWN_HOLOGRAMS.get(player).contains(hologram) && (hologram.isVisibleForAnyone() || hologram.getPlayers().contains(player)) && hologram.getLocation().getWorld().equals(player.getWorld()) && hologram.getLocation().distanceSquared(player.getLocation()) <= (hologram.getVisibilityRadius() * hologram.getVisibilityRadius())) {
                    SHOWN_HOLOGRAMS.get(player).add(hologram);
                    for (final Object entity : hologram.getEntities()) {
                        JeffLib.getNMSHandler().showEntityToPlayer(entity, player);
                    }
                } else {

                    // Hide hologram
                    if (!SHOWN_HOLOGRAMS.get(player).contains(hologram)) {
                        continue;
                    }

                    if (!hologram.getLocation().getWorld().equals(player.getWorld()) || hologram.getLocation().distanceSquared(player.getLocation()) > (hologram.getVisibilityRadius() * hologram.getVisibilityRadius())) {
                        SHOWN_HOLOGRAMS.get(player).remove(hologram);
                        for (final Object entity : hologram.getEntities()) {
                            JeffLib.getNMSHandler().hideEntityFromPlayer(entity, player);
                        }
                    }
                }
            }
        }
    };
    private static boolean IS_SCHEDULED = false;

    /**
     * Removes all holograms for all players
     */
    public static void unloadAllHolograms() {
        for (final Hologram hologram : holograms) {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (SHOWN_HOLOGRAMS.containsKey(player)) {
                    if (SHOWN_HOLOGRAMS.get(player).contains(hologram)) {
                        SHOWN_HOLOGRAMS.get(player).remove(hologram);
                        for (final Object entity : hologram.getEntities()) {
                            JeffLib.getNMSHandler().hideEntityFromPlayer(entity, player);
                        }
                    }
                }
            }
        }
        holograms.clear();
    }


    /**
     * Gets a Hologram from a ConfigurationSection
     */
    public static Hologram loadHologram(final ConfigurationSection section) {
        return Hologram.deserialize(section.getValues(false));
    }


    public static void init() {


        NMSNotSupportedException.check();

        if (!IS_SCHEDULED) {
            IS_SCHEDULED = true;
            Bukkit.getScheduler().runTaskTimer(JeffLib.getPlugin(), RUNNABLE, 5, 5);
        }
    }

}
