package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

// TODO: Javadoc
public class Tasks {

    public static BukkitTask repeat(Runnable runnable, long initialDelay, long delay) {
        JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskTimer(JeffLib.getPlugin(),runnable,initialDelay,delay);
    }

    public static BukkitTask repeatAsync(Runnable runnable, long initialDelay, long delay) {
        JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskTimerAsynchronously(JeffLib.getPlugin(),runnable,initialDelay,delay);
    }

    public static BukkitTask nextTick(Runnable runnable) {
        JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTask(JeffLib.getPlugin(),runnable);
    }

    public static BukkitTask nextTickAsync(Runnable runnable) {
        JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskAsynchronously(JeffLib.getPlugin(), runnable);
    }

    public static BukkitTask later(Runnable runnable, long delay) {
        JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskLater(JeffLib.getPlugin(),runnable, delay);
    }

    public static BukkitTask laterAsync(Runnable runnable, long delay) {
        JeffLibNotInitializedException.check();
        return Bukkit.getScheduler().runTaskLaterAsynchronously(JeffLib.getPlugin(), runnable, delay);
    }
}
