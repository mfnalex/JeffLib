package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

// TODO: Javadoc
public final class Tasks {

    public static BukkitTask repeat(final Runnable runnable, final long initialDelay, final long delay) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskTimer(JeffLib.getPlugin(),runnable,initialDelay,delay);
    }

    public static BukkitTask repeatAsync(final Runnable runnable, final long initialDelay, final long delay) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskTimerAsynchronously(JeffLib.getPlugin(),runnable,initialDelay,delay);
    }

    public static BukkitTask nextTick(final Runnable runnable) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTask(JeffLib.getPlugin(),runnable);
    }

    public static BukkitTask nextTickAsync(final Runnable runnable) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskAsynchronously(JeffLib.getPlugin(), runnable);
    }

    public static BukkitTask sync(final Runnable runnable) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTask(JeffLib.getPlugin(), runnable);
    }

    public static BukkitTask async(final Runnable runnable) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskAsynchronously(JeffLib.getPlugin(), runnable);
    }

    public static BukkitTask later(final Runnable runnable, final long delay) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskLater(JeffLib.getPlugin(),runnable, delay);
    }

    public static BukkitTask laterAsync(final Runnable runnable, final long delay) {
        // JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskLaterAsynchronously(JeffLib.getPlugin(), runnable, delay);
    }
}
