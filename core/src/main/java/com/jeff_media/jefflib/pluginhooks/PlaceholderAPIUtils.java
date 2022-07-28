package com.jeff_media.jefflib.pluginhooks;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class PlaceholderAPIUtils {

    public static boolean register(@Nonnull final BiFunction<OfflinePlayer, String, String> function) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIHandler.register(function);
            return true;
        }
        return false;
    }

    public static boolean register(@Nonnull final String placeholder, @Nonnull final Function<OfflinePlayer, String> function) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIHandler.register(placeholder, function);
            return true;
        }
        return false;
    }

}
