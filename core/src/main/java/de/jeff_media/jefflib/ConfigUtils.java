package de.jeff_media.jefflib;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import javax.annotation.Nonnull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Configuration file related methods
 */
public final class ConfigUtils {

    /**
     * Turns a ConfigurationSection into a Map&lt;String,Object&gt; (just like SnakeYAML)
     */
    public static Map<String, Object> asMap(@Nonnull final ConfigurationSection section) {
        final Map<String, Object> map = new HashMap<>();
        section.getKeys(false).forEach(key -> map.put(key, section.get(key)));
        return map;
    }

    /**
     * Loads a custom config file like the builtin config. That means it will
     * <ol>
     * <li>Save the default config from the plugin's resources if it's not already saved</li>
     * <li>Load the saved file</li>
     * <li>Adds all the default values from the included config</li>
     * </ol>
     *
     * @param filename Filename (without leading /)
     */
    public static FileConfiguration getConfig(final String filename) {
        // JeffLibNotInitializedException.check();
        final File file = new File(JeffLib.getPlugin().getDataFolder(), filename);
        if (!file.exists()) {
            JeffLib.getPlugin().saveResource(filename, false);
        }

        final YamlConfiguration defaultConfig = new YamlConfiguration();

        try (final InputStream inputStream = JeffLib.getPlugin().getResource(filename); final Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
            defaultConfig.load(reader);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Could not load resource " + filename);
        } catch (final InvalidConfigurationException exception) {
            throw new IllegalArgumentException("Invalid default config for " + filename, exception);
        }

        final YamlConfiguration config = new YamlConfiguration();
        config.setDefaults(defaultConfig);
        try {
            config.load(file);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Could not file " + filename);
        } catch (final InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        return config;
    }

}
