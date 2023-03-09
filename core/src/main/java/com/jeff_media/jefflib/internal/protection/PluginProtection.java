package com.jeff_media.jefflib.internal.protection;

import javax.annotation.Nonnull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface PluginProtection {

    boolean canBuild(@Nonnull final Player player, @Nonnull final Location location);

    default boolean canBreak(@Nonnull final Player player, @Nonnull final Location location) {
        return canBuild(player, location);
    }

}
