package de.jeff_media.jefflib;

import de.jeff_media.jefflib.internal.listeners.BlockTrackListener;
import de.jeff_media.jefflib.internal.listeners.PlayerScrollListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main class of the library, has to be initialized for certain methods to work.
 */
public class JeffLib {

    private static final Random random = new Random();

    /**
     * Returns the ThreadLocalRandom instance.
     * @return ThreadLocalRandom instance
     */
    public static ThreadLocalRandom getThreadLocalRandom() {
        return threadLocalRandom;
    }

    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    private static Plugin main;

    private JeffLib() {
    }

    /**
     * Returns the Plugin instance.
     * @return Plugin instance
     */
    public static Plugin getPlugin() {
        return main;
    }

    /**
     * Returns the Random instance.
     * @return Random instance
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * Initializes the library. Needed for some methods.
     * @param plugin Plugin instance
     * @param trackBlocks Whether to use the BlockTracker feature. Only available in 1.16.3 and later.
     */
    public static void init(Plugin plugin, boolean trackBlocks) {
        main = plugin;
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), main);
        if (trackBlocks) {
            if (McVersion.isAtLeast(1, 16, 3)) {
                Bukkit.getPluginManager().registerEvents(new BlockTrackListener(), main);
            } else {
                plugin.getLogger().info("You are using an MC version below 1.16.3 - Block Tracking features will be disabled.");
            }
        }
    }

    /**
     * Initializes the Library
     *
     * @param plugin Plugin instance
     */
    public static void init(Plugin plugin) {
        init(plugin, true);
    }

    /**
     * Checks whether Spigot or a fork is running
     *
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

}
