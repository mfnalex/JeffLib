package de.jeff_media.jefflib.internal.blackhole;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import de.jeff_media.jefflib.internal.InternalOnly;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@InternalOnly
public class WorldGuardHandler {

    public static List<String> getRegionsAtLocation(Location location) {
        List<String> regionList = new ArrayList<>();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(location));
        for (ProtectedRegion region : set.getRegions()) {
            regionList.add(region.getId());
        }
        return regionList;
    }
}
