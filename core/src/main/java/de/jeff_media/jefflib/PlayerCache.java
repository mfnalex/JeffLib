package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerCache {

    private static final File cacheFile;
    private static final YamlConfiguration cache;

    static {
        if(JeffLib.getPlugin()==null) {
            throw new JeffLibNotInitializedException();
        }
        cacheFile = new File(JeffLib.getPlugin().getDataFolder(), "playercache.yml");
        cache = YamlConfiguration.loadConfiguration(cacheFile);
    }

    @NotNull
    public static String getName(OfflinePlayer player) {
        String name = player.getName();
        if(name!=null) {
            cache.set(player.getUniqueId().toString(),name);
            return name;
        }
        return cache.getString(player.getUniqueId().toString(),"Unknown Player (" + player.getUniqueId().toString().split("-")[0] + ")");
    }

    public static String getName(UUID uuid) {
        return getName(Bukkit.getOfflinePlayer(uuid));
    }

    public static void save() {
        try {
            cache.save(cacheFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
