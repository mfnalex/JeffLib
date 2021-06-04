package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

public class CommandUtils {

    public static void registerCommand(final String permission, final String... aliases) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        final PluginCommand command = getCommand(aliases[0], JeffLib.getPlugin());

        command.setAliases(Arrays.asList(aliases));
        command.setPermission(permission);

        getCommandMap().register(JeffLib.getPlugin().getDescription().getName(), command);
    }

    private static PluginCommand getCommand(final String name, final Plugin plugin) {
        PluginCommand command = null;

        try {
            final Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
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
