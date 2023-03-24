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

import com.jeff_media.jefflib.internal.protection.LandsProtection;
import com.jeff_media.jefflib.internal.protection.PlotSquared6Protection;
import com.jeff_media.jefflib.internal.protection.PluginProtection;
import com.jeff_media.jefflib.internal.protection.WorldGuardProtection;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility class for common protection checks.
 */
@UtilityClass
public class ProtectionUtils {

    private final List<PluginProtection> PLUGIN_PROTECTIONS = new ArrayList<>();
    private static boolean initialized = false;

    void loadPluginProtections() {
        PLUGIN_PROTECTIONS.clear();
        register("WorldGuard", WorldGuardProtection::new);
        register("Lands", LandsProtection::new);
        register("PlotSquared", PlotSquared6Protection::new);
        initialized = true;
    }

    private void register(String pluginName, Supplier<PluginProtection> supplier) {
        Plugin protectionPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (protectionPlugin != null) {
            try {
                PLUGIN_PROTECTIONS.add(supplier.get());
                if (!initialized)
                    JeffLib.getLogger().info("Hooked into " + protectionPlugin.getName() + " " + protectionPlugin.getDescription().getVersion());
            } catch (Exception exception) {
                if (!initialized)
                    JeffLib.getLogger().warning("Could not hook into " + protectionPlugin.getName() + " " + protectionPlugin.getDescription().getVersion());
            }
        }
    }

    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        for (PluginProtection pluginProtection : PLUGIN_PROTECTIONS) {
            if (!pluginProtection.canBuild(player, location)) {
                return false;
            }
        }
        return true;
    }

    public boolean canBreak(@NotNull Player player, @NotNull Location location) {
        for (PluginProtection pluginProtection : PLUGIN_PROTECTIONS) {
            if (!pluginProtection.canBreak(player, location)) {
                return false;
            }
        }
        return true;
    }
}
