package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Command registration related methods
 */
public class CommandUtils {

    /**
     * Registers a new command
     * @param permission Required permission or null
     * @param aliases Aliases of the command. First element is the "real" command name.
     */
    public static void registerCommand(final @Nullable String permission, final String... aliases) {

        if(JeffLib.getPlugin() == null) {
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
            final Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, JeffLib.getPlugin());
        } catch (final SecurityException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return command;
    }

    private static CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                final Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (final NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }

        return commandMap;
    }
}
