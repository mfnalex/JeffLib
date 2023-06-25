/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Sends messages to CommandSenders while applying color codes, gradients, placeholders and emojis
 */
@UtilityClass
public class Msg {

    /**
     * Sends a message to the given CommandSender, translating all placeholders / color codes
     *
     * @param receiver CommandSender to receive the message
     * @param text     Text to send
     * @param player   OfflinePlayer to use for replacing PlaceholderAPI placeholders, or null
     */
    public static void send(final CommandSender receiver, final String text, @Nullable final OfflinePlayer player) {
        receiver.sendMessage(TextUtils.format(text, player));
    }

    /**
     * Sends a message to the given CommandSender, translating all placeholders / color codes. When the CommandSender is instanceof {@link Player}, it will be used to translate the PlaceholderAPI placeholders.
     *
     * @param receiver CommandSender to receive the message
     * @param text     Text to send
     */
    public static void send(final CommandSender receiver, final String text) {
        if (receiver instanceof Player) {
            receiver.sendMessage(TextUtils.format(text, (OfflinePlayer) receiver));
        } else {
            receiver.sendMessage(TextUtils.format(text));
        }
    }
}
