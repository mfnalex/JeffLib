package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.Hologram;
import de.jeff_media.jefflib.data.worldboundingbox.WorldBoundingBox;
import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import de.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import de.jeff_media.jefflib.internal.listeners.BlockTrackListener;
import de.jeff_media.jefflib.internal.listeners.PlayerScrollListener;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
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
    @Getter private static String version = "n/a";
    private static AbstractNMSHandler abstractNmsHandler;

    public static AbstractNMSHandler getNMSHandler() {
        return abstractNmsHandler;
    }

    /**
     * Returns the {@link ThreadLocalRandom} instance.
     *
     * @return ThreadLocalRandom instance
     */
    public static ThreadLocalRandom getThreadLocalRandom() {
        return threadLocalRandom;
    }

    /**
     * Returns the {@link Plugin} instance that initialized JeffLib.
     *
     * @return Plugin instance
     */
    public static Plugin getPlugin() {
        return main;
    }

    /**
     * Returns the {@link Random} instance.
     *
     * @return Random instance
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * Registers the listeners needed to call the {@link de.jeff_media.jefflib.events.PlayerScrollEvent}
     */
    public static void registerPlayerScrollEvent() {
        JeffLibNotInitializedException.check();
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), main);
    }

    /**
     * Registers the listeners needed to track blocks using {@link BlockTracker}
     */
    public static void registerBlockTracker() {
        if (McVersion.isAtLeast(1, 16, 3)) {
            Bukkit.getPluginManager().registerEvents(new BlockTrackListener(), main);
        } else {
            main.getLogger().info("You are using an MC version below 1.16.3 - Block Tracking features will be disabled.");
        }
    }

    /**
     * Initializes the library. Needed for some methods.
     *
     * @param plugin      Plugin instance
     */
    public static void init(final Plugin plugin) {
        main = plugin;
        ConfigurationSerialization.registerClass(Hologram.class,plugin.getName().toLowerCase(Locale.ROOT)+"Hologram");
        ConfigurationSerialization.registerClass(WorldBoundingBox.class, plugin.getName().toLowerCase(Locale.ROOT)+"WorldBoundingBox");

        try {
            version = FileUtils.readFileFromResources(plugin, "jefflib.version").get(0);
        } catch (final Throwable ignored) {

        }

        try {
            final String packageName = JeffLib.class.getPackage().getName();
            final String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            //abstractNmsHandler = (AbstractNMSHandler) Class.forName(packageName + ".internal.nms." + internalsName + ".NMSHandler").newInstance();
            abstractNmsHandler = (AbstractNMSHandler) Class.forName(packageName + ".internal.nms." + internalsName + ".NMSHandler").getDeclaredConstructor().newInstance();
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | NoSuchMethodException | InvocationTargetException exception) {
            plugin.getLogger().severe("The included JeffLib version (" + version + ")does not fully support the Minecraft version you are currently running:");
            exception.printStackTrace();
        }

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
