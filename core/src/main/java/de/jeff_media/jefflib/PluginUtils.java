package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.function.Supplier;

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

    public static <T> T whenInstalled(final String plugin, final Supplier<T> supplier, final T defaultValue) {
        try {
            if (isInstalledAndEnabled(plugin)) {
                try {
                    return supplier.get();
                } catch (final Exception ignored) {

                }
            }
            return defaultValue;
        } catch (final Exception | Error e) {
            return defaultValue;
        }
    }

}
