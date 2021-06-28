package de.jeff_media.jefflib;

import de.jeff_media.jefflib.internal.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Main class of the library
 */
public class JeffLib {

    private static Plugin main;

    private static final Random random = new Random();

    public static Plugin getPlugin() {
        return main;
    }

    public static Random getRandom() {
        return random;
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

    /**
     * Checks whether Spigot or a fork is running
     * @return true when running at least Spigot
     */
    public static boolean isRunningSpigot() {
        try {
            Class.forName("net.md_5.bungee.api.ChatColor");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
    
    public static class Messages {
        public static final String NOT_INITIALIZED = "\"JeffLib hasn't been initialized. Use JeffLib#init before using this method.\"";
    }

    public static <T> T getRandomFromCollection(Collection<T> collection) {
        int index = random.nextInt(collection.size());
        if(collection instanceof List) {
            return ((List<T>) collection).get(index);
        }
        return (T) collection.toArray()[index];
    }

}
