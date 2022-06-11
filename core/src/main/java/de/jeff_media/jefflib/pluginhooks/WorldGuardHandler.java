//package de.jeff_media.jefflib.internal.blackhole;
//
//import com.sk89q.worldedit.bukkit.BukkitAdapter;
//import com.sk89q.worldguard.LocalPlayer;
//import com.sk89q.worldguard.WorldGuard;
//import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
//import com.sk89q.worldguard.protection.ApplicableRegionSet;
//import com.sk89q.worldguard.protection.flags.Flags;
//import com.sk89q.worldguard.protection.flags.StateFlag;
//import com.sk89q.worldguard.protection.regions.ProtectedRegion;
//import com.sk89q.worldguard.protection.regions.RegionQuery;
//import de.jeff_media.jefflib.ReflUtils;
//import de.jeff_media.jefflib.internal.annotations.Internal;
//import lombok.experimental.UtilityClass;
//import org.bukkit.Location;
//import org.bukkit.entity.Player;
//import javax.annotation.Nonnull;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//@Internal
//@UtilityClass
//public final class WorldGuardHandler {
//
//    private static Method WORLDGUARD_GETINSTANCE;
//    private static Object WORLDGUARD_INSTANCE;
//    private static Method WORLDGUARD_INSTANCE_GETPLATFORM;
//    private static Method WORLDGUARD_INSTANCE_PLATFORM;
//
//    static {
//        try {
//            WORLDGUARD_GETINSTANCE = ReflUtils.getMethodCached(Class.forName("com.sk89q.worldguard.WorldGuard"),"getInstance");
//            WORLDGUARD_INSTANCE = WORLDGUARD_GETINSTANCE.invoke(null,null);
//            WORLDGUARD_INSTANCE_GETPLATFORM = ReflUtils.getMethodCached(WORLDGUARD_INSTANCE.getClass(),"getPlatform");
//            WORLDGUARD_INSTANCE_PLATFORM = WORLDGUARD_INSTANCE_GETPLATFORM.invoke(null, null);
//        } catch (Throwable ignored) {
//
//        }
//    }
//
//    private static ApplicableRegionSet getRegionSet(@Nonnull final Location location) {
//        try {
//            ReflUtils.getMethodCached(Class.forName("com.sk89q.worldguard.WorldGuard"),"getInstance");
//            WorldGuard.getInstance().getPlatform().getRegionContainer();
//            final RegionQuery query = container.createQuery();
//            return query.getApplicableRegions(BukkitAdapter.adapt(location));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Nonnull
//    public static List<String> getRegionsAtLocation(@Nonnull final Location location) {
//        final List<String> regionList = new ArrayList<>();
//        final ApplicableRegionSet set = getRegionSet(location);
//        for (final ProtectedRegion region : set.getRegions()) {
//            regionList.add(region.getId());
//        }
//        return regionList;
//    }
//
//    public static boolean testStateFlag(@Nonnull final Player player, @Nonnull final Location location, @Nonnull final StateFlag flag) {
//        final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
//        final ApplicableRegionSet set = getRegionSet(location);
//        return set.testState(localPlayer, flag);
//    }
//
//    public static boolean canPlace(@Nonnull final Player player, @Nonnull final Location location) {
//        return testStateFlag(player, location, Flags.BUILD) && testStateFlag(player, location, Flags.BLOCK_PLACE);
//    }
//
//    public static boolean canBreak(@Nonnull final Player player, @Nonnull final Location location) {
//        return testStateFlag(player, location, Flags.BUILD) && testStateFlag(player, location, Flags.BLOCK_BREAK);
//    }
//
//    public static boolean canInteract(@Nonnull final Player player, @Nonnull final Location location) {
//        return testStateFlag(player, location, Flags.INTERACT);
//    }
//
//}


package de.jeff_media.jefflib.pluginhooks;

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
import de.jeff_media.jefflib.internal.annotations.Internal;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

@Internal
@UtilityClass
final class WorldGuardHandler {

    private static ApplicableRegionSet getRegionSet(@Nonnull final Location location) {
        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionQuery query = container.createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location));
    }

    @Nonnull
    public static List<String> getRegionsAtLocation(@Nonnull final Location location) {
        final List<String> regionList = new ArrayList<>();
        final ApplicableRegionSet set = getRegionSet(location);
        for (final ProtectedRegion region : set.getRegions()) {
            regionList.add(region.getId());
        }
        return regionList;
    }

    public static boolean testStateFlag(@Nonnull final Player player, @Nonnull final Location location, @Nonnull final StateFlag flag) {
        final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        final ApplicableRegionSet set = getRegionSet(location);
        return set.testState(localPlayer, flag);
    }

    public static boolean canPlace(@Nonnull final Player player, @Nonnull final Location location) {
        return testStateFlag(player, location, Flags.BUILD) && testStateFlag(player, location, Flags.BLOCK_PLACE);
    }

    public static boolean canBreak(@Nonnull final Player player, @Nonnull final Location location) {
        return testStateFlag(player, location, Flags.BUILD) && testStateFlag(player, location, Flags.BLOCK_BREAK);
    }

    public static boolean canInteract(@Nonnull final Player player, @Nonnull final Location location) {
        return testStateFlag(player, location, Flags.INTERACT);
    }

}
