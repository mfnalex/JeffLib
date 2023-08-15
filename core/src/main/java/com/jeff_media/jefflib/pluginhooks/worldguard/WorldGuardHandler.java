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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
@UtilityClass
public class WorldGuardHandler {

    @NotNull
    public static List<String> getRegionsAtLocation(@NotNull final Location location) {
        final List<String> regionList = new ArrayList<>();
        final ApplicableRegionSet set = getRegionSet(location);
        for (final ProtectedRegion region : set.getRegions()) {
            regionList.add(region.getId());
        }
        return regionList;
    }

    private static ApplicableRegionSet getRegionSet(@NotNull final Location location) {
        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionQuery query = container.createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location));
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Location location) {
        //return testBuiltInStateFlag(player, location, Flags.BUILD) && testBuiltInStateFlag(player, location, Flags.BLOCK_PLACE);
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().testBuild(BukkitAdapter.adapt(location), localPlayer, Flags.BLOCK_PLACE)
                || WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, BukkitAdapter.adapt(player.getWorld()));
    }

    public static boolean testBuiltInStateFlag(@Nullable final Player player, @NotNull final Location location, @NotNull final StateFlag flag) {
        final LocalPlayer localPlayer = player == null ? null : WorldGuardPlugin.inst().wrapPlayer(player);
        final ApplicableRegionSet set = getRegionSet(location);
        return set.testState(localPlayer, flag);
    }

    public static boolean testStateFlag(@Nullable final Player player, @NotNull final Location location, @NotNull final com.jeff_media.jefflib.pluginhooks.worldguard.StateFlag flag) {
        if (flag instanceof WorldGuardStateFlag) {
            return testBuiltInStateFlag(player, location, Objects.requireNonNull(((WorldGuardStateFlag) flag).getWorldGuardStateFlag(), "WorldGuardFlag is null"));
        } else {
            throw new IllegalArgumentException("Given flag is not a WorldGuardStateFlag");
        }
    }

    public static boolean canBreak(@NotNull final Player player, @NotNull final Location location) {
        //return testBuiltInStateFlag(player, location, Flags.BUILD) && testBuiltInStateFlag(player, location, Flags.BLOCK_BREAK);
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().testBuild(BukkitAdapter.adapt(location), localPlayer, Flags.BLOCK_BREAK)
                || WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, BukkitAdapter.adapt(player.getWorld()));
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Location location) {
        //return testBuiltInStateFlag(player, location, Flags.INTERACT);
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().testBuild(BukkitAdapter.adapt(location), localPlayer, Flags.INTERACT)
                || WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, BukkitAdapter.adapt(player.getWorld()));
    }

    public static boolean canUse(@NotNull final Player player, @NotNull final Location location) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().testBuild(BukkitAdapter.adapt(location), localPlayer, Flags.USE)
                || WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, BukkitAdapter.adapt(player.getWorld()));
    }

    public static com.jeff_media.jefflib.pluginhooks.worldguard.StateFlag registerStateFlag(String name, com.jeff_media.jefflib.pluginhooks.worldguard.StateFlag.State defaultValue) {
        return new WorldGuardStateFlag(name, defaultValue);
    }

}
