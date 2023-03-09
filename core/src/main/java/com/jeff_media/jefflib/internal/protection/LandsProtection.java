package com.jeff_media.jefflib.internal.protection;

import com.jeff_media.jefflib.JeffLib;
import me.angeschossen.lands.api.flags.Flags;
import me.angeschossen.lands.api.flags.types.RoleFlag;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LandsProtection implements PluginProtection {

    private final LandsIntegration lands = new LandsIntegration(JeffLib.getPlugin());

    @Override
    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        return hasRoleFlag(player, location, Flags.BLOCK_PLACE);
    }

    @Override
    public boolean canBreak(@NotNull Player player, @NotNull Location location) {
        return hasRoleFlag(player, location, Flags.BLOCK_BREAK);
    }

    private boolean hasRoleFlag(Player player, Location location, RoleFlag flag) {
        Area area = lands.getAreaByLoc(location);
        if (area == null) return true;
        return area.hasFlag(player, flag, false);
    }
}
