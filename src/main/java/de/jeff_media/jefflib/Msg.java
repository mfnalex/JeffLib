package de.jeff_media.jefflib;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Msg {

    public static void send(CommandSender receiver, String text, @Nullable OfflinePlayer player) {
        receiver.sendMessage(TextUtils.format(text, player));
    }

    public static void send(CommandSender receiver, String text) {
        if(receiver instanceof Player) {
            receiver.sendMessage(TextUtils.format(text,(Player) receiver));
        } else {
            receiver.sendMessage(TextUtils.format(text));
        }
    }
}
