package de.jeff_media.jefflib;

import de.jeff_media.jefflib.internal.listeners.*;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main class of the library, has to be initialized for certain methods to work.
 */
public class JeffLib {

    private static Plugin main;

    @Getter private static final Random random = new Random();
    @Getter private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public static Plugin getPlugin() {
        return main;
    }

    public static Random getRandom() {
        return random;
    }

    private JeffLib() {}

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

}
