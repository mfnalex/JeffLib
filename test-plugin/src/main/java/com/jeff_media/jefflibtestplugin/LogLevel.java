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

package com.jeff_media.jefflibtestplugin;

import org.bukkit.ChatColor;

public enum LogLevel {
    INFO(null),
    WARNING(ChatColor.YELLOW),
    ERROR(ChatColor.RED);

    private final ChatColor chatColor;

    LogLevel(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public String getChatColor() {
        return chatColor != null ? chatColor.toString() : "";
    }
}
