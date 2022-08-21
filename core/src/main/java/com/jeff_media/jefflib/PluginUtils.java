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

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.function.Supplier;

@UtilityClass
public final class PluginUtils {

    public static <T> T whenInstalled(final String plugin, final Supplier<T> supplier, final T defaultValue) {
        try {
            if (isInstalledAndEnabled(plugin)) {
                try {
                    return supplier.get();
                } catch (final Exception ignored) {

                }
            }
            return defaultValue;
        } catch (final Exception | Error e) {
            return defaultValue;
        }
    }

    /**
     * Checks whether a plugin is installed and enabled
     *
     * @return true when the plugin is installed and enabled, otherwise false
     */
    public static boolean isInstalledAndEnabled(final String name) {
        return Bukkit.getPluginManager().getPlugin(name) != null && Bukkit.getPluginManager().isPluginEnabled(name);
    }

}
