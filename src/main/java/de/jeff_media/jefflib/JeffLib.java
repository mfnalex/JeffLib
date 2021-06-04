package de.jeff_media.jefflib;

import de.jeff_media.jefflib.events.listeners.BlockTrackListener;
import de.jeff_media.jefflib.events.listeners.PlayerScrollListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class JeffLib {

    private static Plugin main;

    public static Plugin getPlugin() {
        return main;
    }

    public static void init(Plugin plugin) {
        main = plugin;
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), main);
        Bukkit.getPluginManager().registerEvents(new BlockTrackListener(), main);
    }

}
