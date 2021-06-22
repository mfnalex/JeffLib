package de.jeff_media.jefflib;

import de.jeff_media.jefflib.events.listeners.BlockTrackListener;
import de.jeff_media.jefflib.events.listeners.PlayerScrollListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Main class of the library
 */
public class JeffLib {

    private static Plugin main;

    public static Plugin getPlugin() {
        return main;
    }

    @Setter @Getter
    private static boolean debug = false;

    /**
     * Outputs debug information
     * @param text
     */
    public static void debug(String text) {
        if(debug) Bukkit.getLogger().warning("[JeffLib Debug] " +text);
    }

    /**
     * Initializes the Library
     * @param plugin Main class of your plugin
     */
    public static void init(Plugin plugin) {
        main = plugin;
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), main);
        Bukkit.getPluginManager().registerEvents(new BlockTrackListener(), main);
    }

}
