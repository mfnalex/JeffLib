package de.jeff_media.jefflib;

import de.jeff_media.jefflib.events.listeners.BlockTrackListener;
import de.jeff_media.jefflib.events.listeners.PlayerScrollListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class JeffLib {

    private static Plugin main;

    public static Plugin getPlugin() {
        return main;
    }

    @Setter @Getter
    private static boolean debug = false;

    public static void debug(String text) {
        if(debug) Bukkit.getLogger().warning("[JeffLib Debug] " +text);
    }

    public static void init(Plugin plugin) {
        main = plugin;
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), main);
        Bukkit.getPluginManager().registerEvents(new BlockTrackListener(), main);
    }

}
