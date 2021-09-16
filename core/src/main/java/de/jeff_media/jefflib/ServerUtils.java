package de.jeff_media.jefflib;

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

}
