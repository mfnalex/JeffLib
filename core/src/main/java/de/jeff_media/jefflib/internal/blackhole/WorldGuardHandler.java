package de.jeff_media.jefflib.internal.blackhole;

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
import de.jeff_media.jefflib.internal.InternalOnly;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@InternalOnly
@UtilityClass
public final class WorldGuardHandler {

    private static ApplicableRegionSet getRegionSet(@NotNull final Location location) {
        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionQuery query = container.createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location));
    }

    @NotNull
    public static List<String> getRegionsAtLocation(@NotNull final Location location) {
        final List<String> regionList = new ArrayList<>();
        final ApplicableRegionSet set = getRegionSet(location);
        for (final ProtectedRegion region : set.getRegions()) {
            regionList.add(region.getId());
        }
        return regionList;
    }

    public static boolean testStateFlag(@NotNull final Player player, @NotNull final Location location, @NotNull final StateFlag flag) {
        final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        final ApplicableRegionSet set = getRegionSet(location);
        return set.testState(localPlayer, flag);
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Location location) {
        return testStateFlag(player, location, Flags.BUILD) && testStateFlag(player, location, Flags.BLOCK_PLACE);
    }

    public static boolean canBreak(@NotNull final Player player, @NotNull final Location location) {
        return testStateFlag(player, location, Flags.BUILD) && testStateFlag(player, location, Flags.BLOCK_BREAK);
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Location location) {
        return testStateFlag(player, location, Flags.INTERACT);
    }

}
