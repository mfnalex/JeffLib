package de.jeff_media.jefflib.internal;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@InternalOnly
@UtilityClass
public final class PluginUtils {

    /**
     * Checks whether a plugin is installed and enabled
     *
     * @return true when the plugin is installed and enabled, otherwise false
     */
    public static boolean isInstalledAndEnabled(final String name) {
        return Bukkit.getPluginManager().getPlugin(name) != null && Bukkit.getPluginManager().isPluginEnabled(name);
    }

}
