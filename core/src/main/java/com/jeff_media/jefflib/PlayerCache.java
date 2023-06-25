/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * Caches player names, UUIDs, Skins, etc.
 *
 * @deprecated Draft API. Will be using async loading / saving and maybe SQLite
 */
@Deprecated
public final class PlayerCache {

    private static final File cacheFile;
    private static final YamlConfiguration cache;

    static {
        cacheFile = new File(JeffLib.getPlugin().getDataFolder(), "playercache.yml");
        cache = YamlConfiguration.loadConfiguration(cacheFile);
    }

    public static String getName(final UUID uuid) {
        return getName(Bukkit.getOfflinePlayer(uuid));
    }

    @NotNull
    public static String getName(final OfflinePlayer player) {
        final String name = player.getName();
        if (name != null) {
            cache.set(player.getUniqueId().toString(), name);
            return name;
        }
        return cache.getString(player.getUniqueId().toString(), "Unknown Player (" + player.getUniqueId().toString().split("-")[0] + ")");
    }

    public static void save() {
        try {
            cache.save(cacheFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
