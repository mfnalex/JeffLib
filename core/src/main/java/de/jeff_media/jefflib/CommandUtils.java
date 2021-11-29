package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Command registration related methods
 */
@UtilityClass
public final class CommandUtils {

    /**
     * Registers a new command
     *
     * @param permission Required permission or null
     * @param aliases    Aliases of the command. First element is the "real" command name.
     */
    public static void registerCommand(@Nullable final String permission, final String... aliases) {

        if (JeffLib.getPlugin() == null) {
            throw new JeffLibNotInitializedException();
        }

        final PluginCommand command = getCommand(aliases[0]);

        command.setAliases(Arrays.asList(aliases));
        command.setPermission(permission);

        getCommandMap().register(JeffLib.getPlugin().getDescription().getName(), command);
    }

    private static PluginCommand getCommand(final String name) {
        PluginCommand command = null;

        try {
            final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            command = constructor.newInstance(name, JeffLib.getPlugin());
        } catch (final SecurityException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return command;
    }

    private static CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);

                commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            }
        } catch (final NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }

        return commandMap;
    }

    public static void sendHelpMessage(final CommandSender sender, final String... args) {
        final Iterator<String> iterator = Arrays.stream(args).iterator();
        while(iterator.hasNext()) {
            final String command = iterator.next();
            sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + command);
            if(iterator.hasNext()) {
                final String message = iterator.next();
                sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + message);
                if(iterator.hasNext()) {
                    sender.sendMessage("");
                }
            }
        }
    }
}
