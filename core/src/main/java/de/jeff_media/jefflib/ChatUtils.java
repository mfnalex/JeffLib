package de.jeff_media.jefflib;

import de.jeff_media.jefflib.internal.blackhole.ChatMuteHandler;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ChatUtils {

    public static boolean isDeaf(@NotNull final Player player) {
        try {
            return ChatMuteHandler.isMuted(player);
        } catch (final Throwable t) {
            return false;
        }
    }

    public static void setDeaf(@NotNull final Player player, final boolean deaf) {
        try {
            ChatMuteHandler.mute(player, deaf);
        } catch (final Throwable ignored) {

        }
    }

}
