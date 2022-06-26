package de.jeff_media.jefflib;

import com.allatori.annotations.DoNotRename;
import de.jeff_media.jefflib.exceptions.JeffLibNotRelocatedException;
import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import de.jeff_media.jefflib.internal.annotations.Internal;
import de.jeff_media.jefflib.internal.annotations.RequiresNMS;
import de.jeff_media.jefflib.internal.listeners.BlockTrackListener;
import de.jeff_media.jefflib.internal.listeners.PlayerScrollListener;
import de.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Main class of the library, has to be initialized for certain methods to work.
 */
@UtilityClass
public final class JeffLib {

    private static final Random random = new Random();
    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    private static Plugin plugin;
    @Getter
    private static boolean debug = false;
    @Getter
    private static String version = "N/A";
    private static AbstractNMSHandler abstractNmsHandler;

    static {
        checkRelocation();
        initialize();
    }

    private static void initialize() {
        // TODO: Tell people to do this themselves in their onLoad()
        //ConfigurationSerialization.registerClass(Hologram.class, plugin.getName().toLowerCase(Locale.ROOT) + "Hologram");
        //ConfigurationSerialization.registerClass(WorldBoundingBox.class, plugin.getName().toLowerCase(Locale.ROOT) + "WorldBoundingBox");

        try {
            version = FileUtils.readFileFromResources(plugin, "/jefflib.version").get(0);
        } catch (final Throwable ignored) {

        }
    }

    /**
     * Checks for proper relocation
     */
    private static void checkRelocation() {
        if (ServerUtils.isRunningMockBukkit()) return;
        final String defaultPackageDe = new String(new byte[]{'d', 'e', '.', 'j', 'e', 'f', 'f', '_', 'm', 'e', 'd', 'i', 'a', '.', 'j', 'e', 'f', 'f', 'l', 'i', 'b'});
        final String defaultPackageCom = new String(new byte[]{'c', 'o', 'm', '.', 'j', 'e', 'f', 'f', '_', 'm', 'e', 'd', 'i', 'a', '.', 'j', 'e', 'f', 'f', 'l', 'i', 'b'});
        final String examplePackage = new String(new byte[]{'y', 'o', 'u', 'r', '.', 'p', 'a', 'c', 'k', 'a', 'g', 'e'});
        final String packageName = JeffLib.class.getPackage().getName();
        if (packageName.startsWith(defaultPackageDe) || packageName.startsWith(defaultPackageCom) || packageName.startsWith(examplePackage)) {
            final String authors = getPlugin().getDescription().getAuthors().stream().collect(Collectors.joining(", "));
            final String plugin = getPlugin().getName() + " " + getPlugin().getDescription().getVersion();
            //throw new JeffLibNotRelocatedException("Nag author(s) " + authors + (authors.length() == 0 ? "" : " ") + "of plugin " + plugin + " for failing to properly relocate JeffLib!");
            getPlugin().getLogger().severe("Nag author(s) " + authors + (authors.length() == 0 ? "" : " ") + "of plugin " + plugin + " for failing to properly relocate JeffLib!");
        }
    }

    /**
     * Only for Unit Tests
     * @internal
     */
    @Internal
    static void setPluginMock(final Plugin plugin) throws IllegalAccessException {
        if(!ServerUtils.isRunningMockBukkit()) {
            throw new IllegalAccessException();
        }
        JeffLib.plugin = plugin;
    }

    /**
     * Returns the {@link Plugin} instance that initialized JeffLib.
     *
     * @return Plugin instance
     */
    @DoNotRename
    public static Plugin getPlugin() {
        if (plugin == null) {
            try {
                plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
            } catch (IllegalArgumentException exception) {
                throw new IllegalStateException("JeffLib: Could not get instance of the providing plugin",exception);
            }
        }
        return plugin;
    }

    /**
     * Prints debug text when debug mode is enabled
     *
     * @see #setDebug(boolean)
     */
    public static void debug(final String text) {
        if (debug) getPlugin().getLogger().info("[JeffLib] [Debug] " + text);
    }

    /**
     * Enables/disables debug mode.
     *
     * @see #debug(String)
     */
    public static void setDebug(final boolean debug) {
        JeffLib.debug = debug;
    }

    /**
     * Returns the {@link AbstractNMSHandler}.
     * @nms
     * @internal
     */
    @DoNotRename
    @Internal
    @RequiresNMS
    public static AbstractNMSHandler getNMSHandler() {
        if(abstractNmsHandler == null) {
            throw new NMSNotSupportedException();
        }
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
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), getPlugin());
    }

    /**
     * Registers the listeners needed to track blocks using {@link BlockTracker}. Requires MC version 1.16.3 or later.
     */
    public static void registerBlockTracker() {
        if (McVersion.current().isAtLeast(1, 16, 3)) {
            Bukkit.getPluginManager().registerEvents(new BlockTrackListener(), getPlugin());
        } else {
            getPlugin().getLogger().info("You are using an MC version below 1.16.3 - Block Tracking features will be disabled.");
        }
    }


    /**
     * Initializes JeffLib. Only required if you call methods requiring your plugin's instance before the plugin has been enabled
     */
    public static void init(final Plugin plugin) {
        JeffLib.plugin = plugin;
    }

    /**
     * Initializes NMS features. This needs to be called for all methods annotated with {@link RequiresNMS}
     * @throws NMSNotSupportedException when the currently NMS version is not supported by this version of JeffLib
     * @nms
     */
    @RequiresNMS
    public static void enableNMS() throws NMSNotSupportedException {
        final String packageName = JeffLib.class.getPackage().getName();
        final String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            abstractNmsHandler = (AbstractNMSHandler) Class.forName(packageName + ".internal.nms." + internalsName + ".NMSHandler").getDeclaredConstructor().newInstance();
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | NoSuchMethodException | InvocationTargetException exception) {
            throw new NMSNotSupportedException("JeffLib " + version + " does not support NMS for " + McVersion.current().toString());
        }
    }

    /**
     * @see #enableNMS()
     */
    @Deprecated
    public static void init(final Plugin plugin, final boolean nms) {
        throw new UnsupportedOperationException();
    }

}
