/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.events.PlayerJumpEvent;
import com.jeff_media.jefflib.events.PlayerScrollEvent;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.annotations.Internal;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.listeners.BlockTrackListener;
import com.jeff_media.jefflib.internal.listeners.PlayerScrollListener;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

/**
 * Main class of the library, has to be initialized for certain methods to work.
 */
@UtilityClass
public final class JeffLib {

    private static final Random random = new Random();
    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    private static boolean checkedRelocation = false;
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

        if (!ServerUtils.isRunningMockBukkit()) {
            try {
                try (final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(JeffLib.class.getResourceAsStream("/jefflib.version")), StandardCharsets.UTF_8))) {
                    version = reader.readLine();
                }
            } catch (final Throwable ex) {
                ex.printStackTrace();
            }

            try (InputStream stream = JeffLib.class.getResourceAsStream("/mcversions.csv")) {
                if (stream == null) {
                    throw new IOException("Could not load mcversions.csv");
                }
                McVersion.loadVersions(stream);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            version = "MOCK";
        }
    }

    /**
     * Gets a logger, if possible the plugin's logger, otherwise the Bukkit logger.
     *
     * @return Plugin's logger if possible, otherwise Bukkit's logger
     */
    public static Logger getLogger() {
        Plugin plugin = getPlugin();
        if (plugin != null) return plugin.getLogger();
        return Bukkit.getLogger();
    }

    /**
     * Checks for proper relocation
     */
    private static void checkRelocation() {
        if (checkedRelocation) return;
        try {
            if (ServerUtils.isRunningMockBukkit()) return;
            final String defaultPackageDe = new String(new byte[]{'d', 'e', '.', 'j', 'e', 'f', 'f', '_', 'm', 'e', 'd', 'i', 'a', '.', 'j', 'e', 'f', 'f', 'l', 'i', 'b'});
            final String defaultPackageCom = new String(new byte[]{'c', 'o', 'm', '.', 'j', 'e', 'f', 'f', '_', 'm', 'e', 'd', 'i', 'a', '.', 'j', 'e', 'f', 'f', 'l', 'i', 'b'});
            final String examplePackage = new String(new byte[]{'y', 'o', 'u', 'r', '.', 'p', 'a', 'c', 'k', 'a', 'g', 'e'});
            final String packageName = JeffLib.class.getPackage().getName();
            if (packageName.startsWith(defaultPackageDe) || packageName.startsWith(defaultPackageCom) || packageName.startsWith(examplePackage)) {
                final String authors = String.join(", ", getPlugin0().getDescription().getAuthors());
                final String plugin = getPlugin().getName() + " " + getPlugin0().getDescription().getVersion();
                //throw new JeffLibNotRelocatedException("Nag author(s) " + authors + (authors.length() == 0 ? "" : " ") + "of plugin " + plugin + " for failing to properly relocate JeffLib!");
                getPlugin().getLogger().severe("Nag author(s) " + authors + (authors.length() == 0 ? "" : " ") + "of plugin " + plugin + " for failing to properly relocate JeffLib!");
            }
        } catch (final Throwable ignored) {
            return;
        }
        checkedRelocation = true;
    }

    /**
     * Only for Unit Tests
     *
     * @internal
     */
    @Internal
    static void setPluginMock(final Plugin plugin) throws IllegalAccessException {
        if (!ServerUtils.isRunningMockBukkit()) {
            throw new IllegalAccessException();
        }
        JeffLib.plugin = plugin;
    }

    private static Plugin getPlugin0() {
        if (plugin == null) {
            plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
        }
        return plugin;
    }

    /**
     * Returns the {@link Plugin} instance that initialized JeffLib.
     *
     * @return Plugin instance
     */
    @DoNotRename
    public static Plugin getPlugin() {
        if (plugin == null) {
            checkRelocation();
            try {
                plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
            } catch (final IllegalArgumentException | IllegalStateException exception) {
                List<String> errorLocation = new ArrayList<>();
                String errorLocation2 = "";
                try {
                    final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                    final PluginDescriptionFile description = getPluginDescriptionFile();
                    final String main = description.getMain();
                    StackTraceElement firstFoundElement = null;
                    for (final StackTraceElement element : elements) {
                        if (element.getClassName().equals(main)) {
                            if (firstFoundElement == null) firstFoundElement = element;
                            final String lineNumber = element.getLineNumber() <= 0 ? "?" : String.valueOf(element.getLineNumber());
                            errorLocation.add(element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + lineNumber + ")");
                        }
                    }
                    if (firstFoundElement != null) {
                        final String lineNumber = firstFoundElement.getLineNumber() <= 0 ? "?" : String.valueOf(firstFoundElement.getLineNumber());
                        errorLocation2 = firstFoundElement.getFileName() + " at line " + lineNumber;
                    }
                } catch (final Throwable ignored) {

                }
                Logger logger = Bukkit.getLogger();
                if (errorLocation.isEmpty()) {
                    logger.severe("[JeffLib] Oh no! I couldn't find the instance of your plugin!");
                    logger.severe("[JeffLib] It seems like you're trying to use JeffLib before your plugin was enabled by the PluginManager.");
                    logger.severe("[JeffLib]");
                    logger.severe("[JeffLib] Please either wait until your plugin's onLoad() or onEnable() method was called, or call");
                    logger.severe("[JeffLib] \"JeffLib.init(this)\" in your plugin's constructor or init block.");
                } else {
                    logger.severe("[JeffLib] Oh no! You're trying to access one of JeffLib's methods before your plugin was enabled at the following location:");
                    logger.severe("[JeffLib]");
                    for (String element : errorLocation) {
                        logger.severe("[JeffLib]   " + element);
                    }
                    logger.severe("[JeffLib]");
                    logger.severe("[JeffLib] Please call \"JeffLib.init(this)\" before doing whatever you do in " + errorLocation2 + ", or wait until your plugin's onLoad() or onEnable() method was called.");
                }

                throw new IllegalStateException();
            }
        }

        return plugin;
    }

    private static PluginDescriptionFile getPluginDescriptionFile() throws NoSuchFieldException, IllegalAccessException {
        URLClassLoader loader = (URLClassLoader) JeffLib.class.getClassLoader();
        Field descriptionField = loader.getClass().getDeclaredField("description");
        descriptionField.setAccessible(true);
        return (PluginDescriptionFile) descriptionField.get(loader);
    }

    /**
     * Prints debug text when debug mode is enabled
     *
     * @see #setDebug(boolean)
     */
    public static void debug(final String text) {
        if (debug) getLogger().info("[JeffLib] [Debug] " + text);
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
     *
     * @nms
     * @internal
     */
    @DoNotRename
    @Internal
    @NMS
    public static AbstractNMSHandler getNMSHandler() {
        if (abstractNmsHandler == null) {
            enableNMS();
            if (abstractNmsHandler == null) {
                throw new NMSNotSupportedException();
            }
        }
        return abstractNmsHandler;
    }

    /**
     * Initializes NMS features. This needs to be called for all methods annotated with {@link NMS}
     *
     * @throws NMSNotSupportedException when the currently NMS version is not supported by this version of JeffLib
     * @nms
     */
    @NMS
    public static void enableNMS() throws NMSNotSupportedException {
        final String packageName = JeffLib.class.getPackage().getName();
        final String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            abstractNmsHandler = (AbstractNMSHandler) Class.forName(packageName + ".internal.nms." + internalsName + ".NMSHandler").getDeclaredConstructor().newInstance();
        } catch (final ReflectiveOperationException exception) {
            final String className = ClassUtils.listAllClasses().stream().filter(name -> name.endsWith(internalsName + ".NMSHandler")).findFirst().orElse(null);
            if (className != null) {
                try {
                    abstractNmsHandler = (AbstractNMSHandler) Class.forName(className).getDeclaredConstructor().newInstance();
                } catch (final ReflectiveOperationException ignored) {

                }
            }
        }
        if (abstractNmsHandler == null) {
            throw new NMSNotSupportedException("JeffLib " + version + " does not support NMS for " + McVersion.current().getName());
        }
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
     * Registers the listeners needed to call the {@link PlayerScrollEvent}
     */
    public static void registerPlayerScrollEvent() {
        Bukkit.getPluginManager().registerEvents(new PlayerScrollListener(), getPlugin());
    }

    /**
     * Registers the listener needed to call the {@link PlayerJumpEvent}
     */
    public static void registerPlayerJumpEvent() {
        PlayerJumpEvent.registerListener();
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
        checkRelocation();
    }

    /**
     * @see #enableNMS()
     */
    @Deprecated
    public static void init(final Plugin plugin, final boolean nms) {
        throw new UnsupportedOperationException();
    }

}
