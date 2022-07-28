package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.Config;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

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
    public static Config getConfig(final String filename) {
        return new Config(filename);
    }

}
