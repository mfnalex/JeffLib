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
