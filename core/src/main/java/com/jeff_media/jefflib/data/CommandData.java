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

package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @deprecated
 */
@Deprecated
public class CommandData {

    private final List<StoredCommandList> storedCommandListList;

    private CommandData(List<StoredCommandList> storedCommandListList) {
        this.storedCommandListList = storedCommandListList;
    }

    public static CommandData of(String command) {
        List<String> commands = new ArrayList<>();
        commands.add(command);
        StoredCommandList commandList = new StoredCommandList(commands, CommandSenderType.CONSOLE);
        List<StoredCommandList> storedCommandLists = new ArrayList<>();
        storedCommandLists.add(commandList);
        return new CommandData(storedCommandLists);
    }

//    public static CommandData of(Object object) {
//        if(object instanceof String) {
//            return of((String)object);
//        } else if(object instanceof List) {
//            List<?> list = (List<?>) object;
//
//        }
//    }

    public enum CommandSenderType {
        CONSOLE,
        PLAYER;

        public static CommandSenderType fromString(String string) {
            if (string.equalsIgnoreCase("console")) return CONSOLE;
            if (string.equalsIgnoreCase("player")) return PLAYER;
            throw new IllegalArgumentException("Invalid CommandSenderType: " + string + ". Valid values are: console, player");
        }
    }

    @Getter
    public static class StoredCommandList {
        private final List<String> commands;
        private final CommandSenderType sender;

        public StoredCommandList(List<String> commands, CommandSenderType sender) {
            this.commands = commands;
            this.sender = sender;
        }

        public static StoredCommandList fromMap(Map<String, Object> map) {
            Object senderType = map.getOrDefault("sender", "console");
            String senderTypeString;
            if (senderType instanceof String) {
                senderTypeString = (String) senderType;
            } else {
                senderTypeString = String.valueOf(senderType);
            }
            Object runList = map.get("run");
            return fromObjects(senderTypeString, runList);
        }

        public static StoredCommandList fromConfigurationSection(ConfigurationSection section) {
            String senderType = section.getString("sender", "console");
            Object runList = section.get("run");
            return fromObjects(senderType, runList);
        }

        @SuppressWarnings("unchecked")
        public static StoredCommandList fromMapOrConfigurationSection(Object section) {
            if (section instanceof Map) {
                return fromMap((Map<String, Object>) section);
            } else if (section instanceof ConfigurationSection) {
                return fromConfigurationSection((ConfigurationSection) section);
            } else {
                throw new IllegalArgumentException("Expected Map or ConfigurationSection, got " + section.getClass().getSimpleName());
            }
        }

        private static StoredCommandList fromObjects(String sender, Object runList) {
            CommandSenderType senderType = CommandSenderType.fromString(sender);
            List<String> commands = new ArrayList<>();
            if (runList instanceof String) {
                commands.add((String) runList);
            } else if (runList instanceof List) {
                for (Object item : ((List<?>) runList)) {
                    if (item instanceof String) {
                        commands.add((String) item);
                    } else {
                        commands.add(String.valueOf(item));
                    }
                }
            }
            return new StoredCommandList(commands, senderType);
        }

        public boolean runCommandsAsConsole() {
            boolean result = true;
            for(String command : commands) {
                result &= Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), papi(null, command));
            }
            return result;
        }

        public boolean runCommandsAsPlayer(Player player) {
            boolean result = true;
            for(String command : commands) {
                result &= player.performCommand(papi(player, command));
            }
            return result;
        }

        private static String papi(@Nullable Player player, String command) {
            return TextUtils.replacePlaceholders(command, player);
        }
    }


}
