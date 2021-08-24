package de.jeff_media.jefflib;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * Sends messages to CommandSenders while applying color codes, gradients, placeholders and emojis
 */
public class Msg {

    /**
     * Sends a message to the given CommandSender, translating all placeholders / color codes
     * @param receiver CommandSender to receive the message
     * @param text Text to send
     * @param player OfflinePlayer to use for replacing PlaceholderAPI placeholders, or null
     */
    public static void send(CommandSender receiver, String text, @Nullable OfflinePlayer player) {
        receiver.sendMessage(TextUtils.format(text, player));
    }

    /**
     * Sends a message to the given CommandSender, translating all placeholders / color codes. When the CommandSender is instanceof {@link Player}, it will be used to translate the PlaceholderAPI placeholders.
     * @param receiver CommandSender to receive the message
     * @param text Text to send
     */
    public static void send(CommandSender receiver, String text) {
        if(receiver instanceof Player) {
            receiver.sendMessage(TextUtils.format(text,(Player) receiver));
        } else {
            receiver.sendMessage(TextUtils.format(text));
        }
    }
}
