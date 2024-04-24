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

import com.jeff_media.jefflib.JeffLib;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LandsProtection implements PluginProtection {

    private final LandsIntegration lands = getApi();

    private LandsIntegration getApi() {
            return LandsIntegration.of(JeffLib.getPlugin());
    }

    @Override
    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        return hasRoleFlag(player, location, Flags.BLOCK_PLACE);
    }

    @Override
    public boolean canBreak(@NotNull Player player, @NotNull Location location) {
        return hasRoleFlag(player, location, Flags.BLOCK_BREAK);
    }

    private boolean hasRoleFlag(Player player, Location location, RoleFlag flag) {
        Area area = lands.getArea(location);
        if (area == null) return true;
        return area.hasRoleFlag(player.getUniqueId(), flag);
    }
}
