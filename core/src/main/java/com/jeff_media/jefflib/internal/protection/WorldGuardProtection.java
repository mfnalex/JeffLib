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

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class WorldGuardProtection implements PluginProtection {

    private static boolean testStateFlag(Player player, Location location, StateFlag flag) {
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().testBuild(getWeirdLocation(location), getWeirdPlayer(player), flag);
    }

    private static com.sk89q.worldedit.util.Location getWeirdLocation(Location location) {
        return BukkitAdapter.adapt(location);
    }

    private static LocalPlayer getWeirdPlayer(Player player) {
        return WorldGuardPlugin.inst().wrapPlayer(player);
    }

    @Override
    public boolean canBuild(@Nonnull Player player, @Nonnull Location location) {
        return testStateFlag(player, location, Flags.BLOCK_PLACE);
    }

    @Override
    public boolean canBreak(@Nonnull Player player, @Nonnull Location location) {
        return testStateFlag(player, location, Flags.BLOCK_BREAK);
    }

}
