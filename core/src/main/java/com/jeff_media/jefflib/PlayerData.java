package com.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Provides a YamlConfiguration per player.
 *
 * @deprecated Draft
 */
@Deprecated
public class PlayerData extends YamlConfiguration {

    private final File file;

    /**
     * Creates new PlayerData or loads the existing PlayerData for the given OfflinePlayer
     *
     * @param offlinePlayer player
     */
    public PlayerData(final OfflinePlayer offlinePlayer) {
        this(offlinePlayer.getUniqueId());
    }

    /**
     * Creates new PlayerData or loads the existing PlayerData for the given UUID
     *
     * @param uniqueId player's UUID
     */
    public PlayerData(final UUID uniqueId) {
        this.file = new File(new File(JeffLib.getPlugin().getDataFolder(), "playerdata"), uniqueId.toString() + ".yml");
        try {
            load(file);
        } catch (final IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Saves the data to file
     *
     * @throws IOException exception
     */
    public final void save() throws IOException {
        save(file);
    }

    /**
     * Saves the data to file asynchronously
     */
    public void saveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(JeffLib.getPlugin(), () -> {
            try {
                save();
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
