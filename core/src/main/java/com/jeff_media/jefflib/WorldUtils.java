package com.jeff_media.jefflib;

import org.bukkit.World;

import javax.annotation.Nonnull;

public final class WorldUtils {

    /**
     * Sets the full game time for a world without calling {@link org.bukkit.event.world.TimeSkipEvent}
     */
    public static void setFullTimeWithoutTimeSkipEvent(@Nonnull final World world, final long time, final boolean notifyPlayers) {
        JeffLib.getNMSHandler().setFullTimeWithoutTimeSkipEvent(world,time,notifyPlayers);
    }
}
