package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * @deprecated "Draft"
 */
@Deprecated()
public class PlayerData {

    private final YamlConfiguration yaml;
    private final OfflinePlayer player;
    private final JavaPlugin plugin;
    private final File file;

    public PlayerData(OfflinePlayer player, JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
        this.file = getFile(player,plugin);
        this.yaml = YamlConfiguration.loadConfiguration(file);
    }

    private static File getFile(OfflinePlayer player, JavaPlugin plugin) {
        return new File(new File(plugin.getDataFolder(),"playerdata"),player.getUniqueId()+".yml");
    }

    public YamlConfiguration get() {
        return yaml;
    }

    public void save() throws IOException {
        yaml.save(file);
    }

    public void saveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
