package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.TPS;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        } catch (Exception ignored) {
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
     * Gets whether this server is running is Spigot or a fork of Spigot.
     * @return True when running Spigot or a fork of Spigot, false if it's only CraftBukkit
     */
    public static boolean isRunningSpigot() {
        return ClassUtils.exists("net.md_5.bungee.api.ChatColor");
    }

    /**
     * Represents the server's current life phase
     */
    public enum ServerLifePhase {
        STARTUP, RUNNING, SHUTDOWN, UNKNOWN
    }

    /**
     * Returns the server's current life phase - when you call this in onEnable or onDisable, and it returns RUNNING, it means the server is reloading.
     * @return Server's life phase
     */
    public ServerLifePhase getLifePhase() {
        try {
            final Field currentTickField = Bukkit.getScheduler().getClass().getDeclaredField("currentTick");
            currentTickField.setAccessible(true);
            final int currentTick = currentTickField.getInt(Bukkit.getScheduler());
            if(currentTick==-1) {
                return ServerLifePhase.STARTUP;
            }
            final Method getServerMethod = Bukkit.getServer().getClass().getMethod("getServer");
            final Object nmsServer = getServerMethod.invoke(Bukkit.getServer());
            final Method isRunningMethod = nmsServer.getClass().getMethod("isRunning");
            return ((boolean) isRunningMethod.invoke(nmsServer)) ? ServerLifePhase.RUNNING : ServerLifePhase.SHUTDOWN;
        } catch (final NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            return ServerLifePhase.UNKNOWN;
        }
    }

    /**
     * Gets the server's last {@link de.jeff_media.jefflib.data.TPS}
     */
    public static TPS getTps() {
        return new TPS(JeffLib.getNMSHandler().getTps());
    }

}
