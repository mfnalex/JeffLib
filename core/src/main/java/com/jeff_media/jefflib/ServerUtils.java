package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.TPS;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Server related methods
 */
@UtilityClass
public class ServerUtils {

    private static final Field CURRENT_TICK_FIELD;

    private static final Object NMS_SERVER_OBJECT;

    private static final Method IS_RUNNING_METHOD;

    static {
        Field tmpCurrentTickField;
        try {
            tmpCurrentTickField = Bukkit.getScheduler().getClass().getDeclaredField("currentTick");
            tmpCurrentTickField.setAccessible(true);
        } catch (final Exception ignored) {
            tmpCurrentTickField = null;
        }
        CURRENT_TICK_FIELD = tmpCurrentTickField;
        Object tmpNmsServerObject;
        Method tmpIsRunningMethod;
        try {
            final Method getServerMethod = Bukkit.getServer().getClass().getMethod("getServer");
            tmpNmsServerObject = getServerMethod.invoke(Bukkit.getServer());
            tmpIsRunningMethod = tmpNmsServerObject.getClass().getMethod("isRunning");
        } catch (final Exception ignored) {
            tmpNmsServerObject = null;
            tmpIsRunningMethod = null;
        }
        NMS_SERVER_OBJECT = tmpNmsServerObject;
        IS_RUNNING_METHOD = tmpIsRunningMethod;
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
     * Returns the current tick count, or -1 if the server is still starting up, or -2 if we couldn't get the current tick count.
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
     * Returns the server's current life phase - when you call this in onEnable or onDisable, and it returns RUNNING, it means the server is reloading.
     * @return Server's life phase
     */
    public ServerLifePhase getLifePhase() {
        try {
            int currentTicket = getCurrentTick();
            if(currentTicket==-1) {
                return ServerLifePhase.STARTUP;
            } else if(currentTicket==-2) {
                return ServerLifePhase.UNKNOWN;
            }
            if(IS_RUNNING_METHOD == null || NMS_SERVER_OBJECT == null) return ServerLifePhase.UNKNOWN;
            return ((boolean) IS_RUNNING_METHOD.invoke(NMS_SERVER_OBJECT)) ? ServerLifePhase.RUNNING : ServerLifePhase.SHUTDOWN;
        } catch (final IllegalAccessException | InvocationTargetException exception) {
            return ServerLifePhase.UNKNOWN;
        }
    }

    /**
     * Gets the server's last {@link TPS}
     */
    public static TPS getTps() {
        return new TPS(JeffLib.getNMSHandler().getTps());
    }

}
