package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @deprecated "Draft"
 */
@Deprecated()
public class PlayerData extends YamlConfiguration {

    private final UUID uuid;
    private final File file;

    public PlayerData(OfflinePlayer offlinePlayer) {
        this(offlinePlayer.getUniqueId());
    }

    public PlayerData(UUID uniqueId) {
        this.uuid = uniqueId;
        this.file = new File(new File(JeffLib.getPlugin().getDataFolder(),"playerdata"),uniqueId.toString()+".yml");
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void save() throws IOException {
        save(file);
    }

    public void saveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(JeffLib.getPlugin(), () -> {
            try {
                save();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
