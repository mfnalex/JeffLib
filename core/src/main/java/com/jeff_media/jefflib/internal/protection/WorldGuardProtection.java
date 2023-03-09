package com.jeff_media.jefflib.internal.protection;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import javax.annotation.Nonnull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
