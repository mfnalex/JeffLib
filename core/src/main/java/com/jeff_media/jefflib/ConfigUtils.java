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

import com.jeff_media.jefflib.data.Config;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.bukkit.configuration.ConfigurationSection;

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
