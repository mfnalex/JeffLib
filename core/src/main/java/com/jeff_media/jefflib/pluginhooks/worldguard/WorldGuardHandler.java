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

package com.jeff_media.jefflib.pluginhooks.worldguard;

import com.jeff_media.jefflib.internal.annotations.Internal;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Internal
@UtilityClass
public class WorldGuardHandler {

    @Nonnull
    public static List<String> getRegionsAtLocation(@Nonnull final Location location) {
        final List<String> regionList = new ArrayList<>();
        final ApplicableRegionSet set = getRegionSet(location);
        for (final ProtectedRegion region : set.getRegions()) {
            regionList.add(region.getId());
        }
        return regionList;
    }

    private static ApplicableRegionSet getRegionSet(@Nonnull final Location location) {
        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionQuery query = container.createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location));
    }

    public static boolean canPlace(@Nonnull final Player player, @Nonnull final Location location) {
        return testBuiltInStateFlag(player, location, Flags.BUILD) && testBuiltInStateFlag(player, location, Flags.BLOCK_PLACE);
    }

    public static boolean testBuiltInStateFlag(@Nullable final Player player, @Nonnull final Location location, @Nonnull final StateFlag flag) {
        final LocalPlayer localPlayer = player == null ? null : WorldGuardPlugin.inst().wrapPlayer(player);
        final ApplicableRegionSet set = getRegionSet(location);
        return set.testState(localPlayer, flag);
    }

    public static boolean testStateFlag(@Nullable final Player player, @Nonnull final Location location, @Nonnull final com.jeff_media.jefflib.pluginhooks.worldguard.StateFlag flag) {
        if (flag instanceof WorldGuardStateFlag) {
            return testBuiltInStateFlag(player, location, ((WorldGuardStateFlag) flag).getWorldGuardStateFlag());
        } else {
            throw new IllegalArgumentException("Given flag is not a WorldGuardStateFlag");
        }
    }

    public static boolean canBreak(@Nonnull final Player player, @Nonnull final Location location) {
        return testBuiltInStateFlag(player, location, Flags.BUILD) && testBuiltInStateFlag(player, location, Flags.BLOCK_BREAK);
    }

    public static boolean canInteract(@Nonnull final Player player, @Nonnull final Location location) {
        return testBuiltInStateFlag(player, location, Flags.INTERACT);
    }

    public static com.jeff_media.jefflib.pluginhooks.worldguard.StateFlag registerStateFlag(String name, com.jeff_media.jefflib.pluginhooks.worldguard.StateFlag.State defaultValue) {
        return new WorldGuardStateFlag(name, defaultValue);
    }

}
