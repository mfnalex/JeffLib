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

package com.jeff_media.jefflib.pluginhooks;

import com.jeff_media.jefflib.PluginUtils;
import com.jeff_media.jefflib.data.worldboundingbox.WorldBoundingBox;
import com.jeff_media.jefflib.exceptions.MissingPluginException;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * WorldEdit related methods. Can be safely used even when WorldEdit is not installed, as long as you catch the {@link MissingPluginException}
 */
@UtilityClass
public class WorldEditUtils {

    /**
     * Checks whether WorldEdit is installed and enabled
     *
     * @return true when WorldEdit is installed and enabled, otherwise false
     */
    public static boolean isWorldEditInstalled() {
        return PluginUtils.isInstalledAndEnabled("WorldEdit");
    }

    /**
     * Returns a {@link WorldBoundingBox} of the player's WorldEdit selection, or null if the player doesn't have any or only an incomplete selection.
     * <p>
     * Example usage:
     *
     * <pre>
     * try {
     *     WorldBoundingBox boundingBox = WorldEditUtils.getSelection(player);
     *     if(boundingBox==null) {
     *         player.sendMessage("You don't have any WorldEdit selection, or it is incomplete.");
     *     } else {
     *         player.sendMessage("Your WorldEdit selection: " + boundingBox);
     *     }
     * } catch (MissingPluginException e) {
     *     player.sendMessage("WorldEdit is not installed!");
     * }
     * </pre>
     *
     * @param player Player to check
     * @return {@link WorldBoundingBox} containing the player's WorldEdit selection, or null if the player doesn't have any or only an incomplete selection.
     * @throws MissingPluginException Exception thrown when WorldEdit is not installed
     */
    public static WorldBoundingBox getSelection(@Nonnull final Player player) throws MissingPluginException {
        try {
            return WorldEditHandler.getCuboidSelection(player);
        } catch (final Throwable throwable) {
            throw new MissingPluginException("WorldEdit");
        }
    }

    public static List<Location> getPolygonSelection(@Nonnull final Player player) throws MissingPluginException {
        try {
            return WorldEditHandler.getPolygonSelection(player);
        } catch (final Throwable throwable) {
            throw new MissingPluginException("WorldEdit");
        }
    }
}
