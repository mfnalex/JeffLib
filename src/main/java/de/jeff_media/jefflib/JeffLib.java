package de.jeff_media.jefflib;

import de.jeff_media.jefflib.internal.listeners.BlockTrackListener;
import de.jeff_media.jefflib.internal.listeners.PlayerScrollListener;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main class of the library, has to be initialized for certain methods to work.
 */
@UtilityClass
public final class JeffLib {

    private static final Random random = new Random();
    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    private static Plugin main;

    /**
     * Returns the ThreadLocalRandom instance.
     *
     * @return ThreadLocalRandom instance
     */
    public static ThreadLocalRandom getThreadLocalRandom() {
        return threadLocalRandom;
    }

    /**
     * Returns the Plugin instance.
     *
     * @return Plugin instance
     */
    public static Plugin getPlugin() {
        return main;
    }

    /**
     * Returns the Random instance.
     *
     * @return Random instance
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * Initializes the library. Needed for some methods.
     *
     * @param plugin      Plugin instance
     * @param trackBlocks Whether to use the BlockTracker feature. Only available in 1.16.3 and later.
     */
    public static void init(final Plugin plugin, final boolean trackBlocks) {
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
    public static void init(final Plugin plugin) {
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
        } catch (final ClassNotFoundException ignored) {
            return false;
        }
    }

}
