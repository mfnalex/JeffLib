/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.TPS;
import java.io.File;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Server related methods
 */
@UtilityClass
public class ServerUtils {

    private static final Field CURRENT_TICK_FIELD;
    private static final InetAddress LOCALHOST;

    static {
        Field tmpCurrentTickField;
        try {
            tmpCurrentTickField = Bukkit.getScheduler().getClass().getDeclaredField("currentTick");
            tmpCurrentTickField.setAccessible(true);
        } catch (final Exception ignored) {
            tmpCurrentTickField = null;
        }
        CURRENT_TICK_FIELD = tmpCurrentTickField;

        InetAddress tmpLocalhost;
        try {
            tmpLocalhost = InetAddress.getLocalHost();
        } catch (final UnknownHostException ignored) {
            tmpLocalhost = null;
        }
        LOCALHOST = tmpLocalhost;
    }

    /**
     * Gets the effective MOTD (after plugins might have changed it)
     *
     * @return The effective MOTD
     */
    public static String getEffectiveMotd() {
        if(LOCALHOST == null) {
            return Bukkit.getMotd();
        }
        ServerListPingEvent event = new ServerListPingEvent(LOCALHOST.getHostName(), LOCALHOST, Bukkit.getMotd(), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers());
        Bukkit.getPluginManager().callEvent(event);
        return event.getMotd();
    }

    /**
     * Gets whether this server is running MockBukkit
     */
    public static boolean isRunningMockBukkit() {
        return Bukkit.getServer().getClass().getName().equals("be.seeseemelk.mockbukkit.ServerMock");
    }

    /**
     * Gets whether this server is running something that supports or claims to support Forge
     */
    public static boolean isRunningForge() {
        return ClassUtils.exists("net.minecraftforge.server.ServerMain");
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
     * Gets the server's working directory
     */
    @Nonnull
    public File getServerFolder() {
        return Paths.get("").toAbsolutePath().toFile();
    }

    /**
     * Represents the server's current life phase
     */
    public enum ServerLifePhase {
        STARTUP, RUNNING, SHUTDOWN, UNKNOWN
    }

    private static final boolean HAS_TRANSLATION_KEY_PROVIDER;

    static {
        boolean hasTranslationKeyProvider = false;
        try {
            EntityType.class.getDeclaredMethod("getTranslationKey");
            hasTranslationKeyProvider = true;
        } catch (ReflectiveOperationException ignored) {

        }
        HAS_TRANSLATION_KEY_PROVIDER = hasTranslationKeyProvider;
    }

    /**
     * Returns whether this server supports getting translation keys without using NMS (latest versions of 1.19.3+)
     * @return true if this server supports getting translation keys without using NMS
     */
    public static boolean hasTranslationKeyProvider() {
        return HAS_TRANSLATION_KEY_PROVIDER;
    }


}
