package de.jeff_media.jefflib;

import org.bukkit.plugin.Plugin;

public class JeffLib {

    private static Plugin main;

    public static Plugin getPlugin() {
        return main;
    }

    public static void init(Plugin plugin) {
        main=plugin;
    }

}
