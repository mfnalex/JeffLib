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

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

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

    /**
     * Saves the data to file
     *
     * @throws IOException exception
     */
    public final void save() throws IOException {
        save(file);
    }
}
