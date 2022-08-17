package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.TPS;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;

/**
 * Server related methods
 */
@UtilityClass
public class ServerUtils {

    private static final Field CURRENT_TICK_FIELD;

    static {
        Field tmpCurrentTickField;
        try {
            tmpCurrentTickField = Bukkit.getScheduler().getClass().getDeclaredField("currentTick");
            tmpCurrentTickField.setAccessible(true);
        } catch (final Exception ignored) {
            tmpCurrentTickField = null;
        }
        CURRENT_TICK_FIELD = tmpCurrentTickField;
    }

    /**
     * Gets whether this server is running MockBukkit
     */
    public static boolean isRunningMockBukkit() {
        return Bukkit.getServer().getClass().getName().equals("be.seeseemelk.mockbukkit.ServerMock");
    }

    /**
     * Gets whether this server is running Spigot or a fork of Spigot.
     *
     * @return True when running Spigot or a fork of Spigot, false if it's only CraftBukkit
     */
    public static boolean isRunningSpigot() {
        return ClassUtils.exists("net.md_5.bungee.api.ChatColor");
    }

    /**
     * Gets whether this server is running Paper or a fork of Paper.
     *
     * @return True when running Paper or a fork of Paper, false otherwise
     */
    public static boolean isRunningPaper() {
        return ClassUtils.exists("com.destroystokyo.paper.PaperConfig");
    }

    /**
     * Returns the server's current life phase - when you call this in onEnable or onDisable, and it returns RUNNING, it means the server is reloading.
     *
     * @return Server's life phase
     */
    public ServerLifePhase getLifePhase() {
        //try {
        int currentTicket = getCurrentTick();
        if (currentTicket == -1) {
            return ServerLifePhase.STARTUP;
        } else if (currentTicket == -2) {
            return ServerLifePhase.UNKNOWN;
        }
        return JeffLib.getNMSHandler().isServerRunnning() ? ServerLifePhase.RUNNING : ServerLifePhase.SHUTDOWN;
    }

    /**
     * Returns the current tick count, or -1 if the server is still starting up, or -2 if we couldn't get the current tick count.
     *
     * @return current tick count, or -1 if the server is still starting up, or -2 if we couldn't get the current tick count.
     */
    public int getCurrentTick() {
        if (CURRENT_TICK_FIELD == null) return -2;
        try {
            return CURRENT_TICK_FIELD.getInt(Bukkit.getScheduler());
        } catch (final IllegalAccessException e) {
            return -2;
        }
    }

    /**
     * Gets the server's last {@link TPS}
     */
    public static TPS getTps() {
        return new TPS(JeffLib.getNMSHandler().getTps());
    }

    /**
     * Represents the server's current life phase
     */
    public enum ServerLifePhase {
        STARTUP, RUNNING, SHUTDOWN, UNKNOWN
    }

    /**
     * Gets the server's working directory
     */
    @Nonnull
    public File getServerFolder() {
        return Paths.get("").toAbsolutePath().toFile();
    }


}
