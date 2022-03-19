package de.jeff_media.jefflib;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {

    public static Map<String,Object> asMap(final @NotNull ConfigurationSection section) {
        final Map<String,Object> map = new HashMap<>();
        section.getKeys(false).forEach(key -> {
            map.put(key,section.get(key));
        });
        return map;
    }

}
