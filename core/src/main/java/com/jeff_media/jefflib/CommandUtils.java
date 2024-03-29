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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.Nullable;

/**
 * Command registration related methods
 */
@UtilityClass
public class CommandUtils {

    private static CommandMap commandMap;

    /**
     * Registers a new command
     *
     * @param permission Required permission or null
     * @param aliases    Aliases of the command. First element is the "real" command name.
     */
    public static void registerCommand(@Nullable final String permission, final String... aliases) {

        final PluginCommand command = getCommand(aliases[0]);

        command.setAliases(Arrays.asList(aliases));
        command.setPermission(permission);

        getCommandMap().register(JeffLib.getPlugin().getDescription().getName(), command);
    }

    /**
     * Gets a PluginCommand by its name
     */
    private static PluginCommand getCommand(final String name) {
        PluginCommand command = null;

        try {
            final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            command = constructor.newInstance(name, JeffLib.getPlugin());
        } catch (final SecurityException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                       IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return command;
    }

    /**
     * Gets the CommandMap
     */
    public static CommandMap getCommandMap() {
        if (commandMap != null) {
            return commandMap;
        }

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);

                commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            }
        } catch (final NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new RuntimeException("Could not get CommandMap", e);
        }

        return commandMap;
    }

    /**
     * Sends a help message with the given commands and descriptions. The list of arguments should be in the following format:
     * <pre>
     * String[] help = {"command1", "Description for command1", "command2", "Description for command2", ...};
     * </pre>
     *
     * @param sender CommandSender to send the help message to
     */
    public static void sendHelpMessage(final CommandSender sender, final HelpStyle style, final String... args) {
        switch (style) {
            case NEW_LINE:
                sendHelpMessageNewLine(sender, args);
                break;
            case SAME_LINE_COMPACT:
                sendHelpMessageSameLine(sender, false, args);
                break;
            case SAME_LINE_SPACED:
            default:
                sendHelpMessageSameLine(sender, true, args);
        }
    }

    private static void sendHelpMessageNewLine(final CommandSender sender, final String... args) {
        final Iterator<String> iterator = Arrays.stream(args).iterator();
        while (iterator.hasNext()) {
            final String command = iterator.next();
            sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + command);
            if (iterator.hasNext()) {
                final String message = iterator.next();
                sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + message);
                if (iterator.hasNext()) {
                    sender.sendMessage("");
                }
            }
        }
    }

    private static void sendHelpMessageSameLine(final CommandSender sender, final boolean newLine, final String... args) {
        final Iterator<String> iterator = Arrays.stream(args).iterator();
        while (iterator.hasNext()) {
            final String command = iterator.next();
            String line = ChatColor.GOLD + "" + ChatColor.BOLD + command + ChatColor.RESET;
            if (iterator.hasNext()) {
                final String description = iterator.next();
                line += " " + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + description;
                sender.sendMessage(line);
                if (iterator.hasNext() && newLine) {
                    sender.sendMessage(" ");
                }
            }
        }
    }

    /**
     * Represents the output style for {@link #sendHelpMessage(CommandSender, HelpStyle, String...)}
     */
    public enum HelpStyle {
        /**
         * Sends command name and command description on two separate lines, including a spacing line. Example:
         * <pre>
         * <b>/command1</b>
         * Description for command1
         *
         * <b>/command2</b>
         * Description for command2
         * </pre>
         */
        NEW_LINE,

        /**
         * Sends command name and command description in one line, including a spacing line. Example:
         * <pre>
         * <b>/command1</b> Description for command1
         *
         * <b>/command2</b> Description for command2
         * </pre>
         */
        SAME_LINE_SPACED,

        /**
         * Sends command name and command description in one line. Example:
         * <pre>
         * <b>/command1</b> Description for command1
         * <b>/command2</b> Description for command2
         * </pre>
         */
        SAME_LINE_COMPACT
    }
}
