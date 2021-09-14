package de.jeff_media.jefflib.pluginhooks;

import de.jeff_media.jefflib.internal.blackhole.PlaceholderAPIHandler;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class PlaceholderAPIUtils {

    public static boolean register(@NotNull final BiFunction<OfflinePlayer, String, String> function) {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIHandler.register(function);
            return true;
        }
        return false;
    }

    public static boolean register(@NotNull final String placeholder, @NotNull final Function<OfflinePlayer, String> function) {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIHandler.register(placeholder, function);
            return true;
        }
        return false;
    }

}
