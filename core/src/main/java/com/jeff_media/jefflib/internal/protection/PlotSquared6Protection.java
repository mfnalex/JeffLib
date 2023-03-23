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

package com.jeff_media.jefflib.internal.protection;

import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlotSquared6Protection implements PluginProtection {

    private static com.plotsquared.core.location.Location getWeirdLocation(Location location) {
        return com.plotsquared.core.location.Location.at(location.getWorld().getName(),
                BlockVector3.at(location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ()));
    }

    @Override
    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        com.plotsquared.core.location.Location weirdLocation = getWeirdLocation(location);
        if (!weirdLocation.isPlotArea()) return true;
        Plot plot = weirdLocation.getPlot();
        if (plot == null) return true;
        UUID uuid = player.getUniqueId();
        return plot.isOwner(uuid) || plot.isAdded(uuid);
    }

}
