package com.jefflib.jefflibtestplugin;

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
