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

package com.jefflib.jefflibtestplugin;

import com.jeff_media.jefflib.ClassUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestRunner implements Runnable {

    private static final String BR = "\n";

    @Getter
    private final JeffLibTestPlugin plugin;
    private final Deque<NMSTest> tests;
    @Nullable
    private final Player player;
    private final Location originalLocation;
    @Getter
    private final World world;
    private final Location spawn;
    @Getter
    private final Block blockInFront;
    private final BukkitTask task;
    private NMSTest currentTest;
    @Getter
    private String session;
    @Setter
    private boolean waitingForTestResult = false;

    public TestRunner(JeffLibTestPlugin plugin, @Nullable Player player) {
        this.plugin = plugin;
        this.player = player;
        this.originalLocation = player != null ? player.getLocation() : null;
        this.tests = getTests(player);
        this.world = plugin.getFlatWorld();
        this.spawn = new Location(world, 0.5, world.getHighestBlockYAt(0, 0) + 1, 0.5, 0, 0);
        this.blockInFront = world.getBlockAt(0, -59, 2);
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 1);
    }

    @NotNull
    private ArrayDeque<NMSTest> getTests(@Nullable Player player) {
        return ClassUtils.listAllClasses().stream().filter(className -> className.startsWith("com.jefflib.jefflibtestplugin.tests.")).map(className -> {
            try {
                Class<?> clazz = Class.forName(className);

                // No inner classes
                if (clazz.isAnonymousClass()) {
                    return null;
                }

                // No abstract classes
                if (Modifier.isAbstract(clazz.getModifiers())) {
                    return null;
                }

                // Only classes implementing NMSTest
                if (!NMSTest.class.isAssignableFrom(clazz)) {
                    throw new RuntimeException("Class " + className + " does not implement NMSTest");
                }

                Class<? extends NMSTest> testClass = clazz.asSubclass(NMSTest.class);
                Constructor<? extends NMSTest> constructor = testClass.getConstructor();
                return constructor.newInstance();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Class " + className + " does not have a no-args constructor");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).filter(Objects::nonNull).sorted().filter((Predicate<NMSTest>) test -> {
            if (player == null) {
                return test.isRunnableFromConsole();
            }
            return true;
        }).collect(Collectors.toCollection(ArrayDeque::new));
    }

    public void beforeEach() {
        if (player != null) {
            player.teleport(spawn);
        }
        world.getEntities().stream().filter(entity -> !(entity instanceof Player)).forEach(Entity::remove);
    }

    public void cleanup() {
        if (player != null) {
            player.teleport(originalLocation);
        }
        task.cancel();
    }

    public boolean hasNext() {
        return !tests.isEmpty();
    }

    public boolean runNext() {

        if (currentTest != null) {
            currentTest.cleanup();
        }

        NMSTest test = currentTest = tests.poll();

        if (test == null) {
            return false;
        }


        return runTest(test);
    }

    private boolean runTest(NMSTest test) {

        session = String.valueOf(UUID.randomUUID());

        waitingForTestResult = true;

        printBanner(ChatColor.GOLD + "Running test: " + ChatColor.AQUA + ChatColor.BOLD + test.getName());
        beforeEach();

        boolean exception = false;

        try {
            if (test instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) test, plugin);
            }
            test.run(this, player);
        } catch (Throwable throwable) {
            throwException(throwable);
            exception = true;
        }

        if (test instanceof Listener) {
            HandlerList.unregisterAll((Listener) test);
        }

        if (exception) {
            return false;
        }

        if (test.hasConfirmation() && player != null) {
            String confirmation = test.getConfirmation();
            if (confirmation == null) {
                throw new NullPointerException("confirmation is null although hasConfirmation() returned true");
            }
            ComponentBuilder builder = new ComponentBuilder();
            builder.append("Please confirm: ").color(ChatColor.GOLD).bold(true).append(BR).reset();
            builder.append(confirmation).color(ChatColor.AQUA).append(BR).reset();
            builder.append("[Yes]").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jefflibtest confirm " + session)).append(" ").reset();
            builder.append("[Repeat]").color(ChatColor.YELLOW).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jefflibtest repeat " + session)).append(" ").reset();
            builder.append("[No]").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jefflibtest error " + session));

            player.spigot().sendMessage(builder.create());

            return false;
        }

        return true;
    }

    private void printBanner(String text) {
        print();
        print(ChatColor.GOLD + Utils.repeat("=", 40));
        print(text);
        print(ChatColor.GOLD + Utils.repeat("=", 40));
        print();
    }

    private void throwException(Throwable throwable) {
        print(LogLevel.ERROR, throwable.getMessage());
        throwable.printStackTrace();
    }

    public void print() {
        print("");
    }

    public void print(Object... objects) {
        print(LogLevel.INFO, objects);
    }

    public void print(LogLevel logLevel, Object... objects) {
        String[] text = Arrays.stream(objects).map(Object::toString).toArray(String[]::new);
        if (player != null) {
            player.sendMessage(text);
        }
        for (String line : text) {
            line = ChatColor.stripColor(line);
            switch (logLevel) {
                case INFO:
                    Bukkit.getLogger().info(line);
                    break;
                case WARNING:
                    Bukkit.getLogger().warning(line);
                    break;
                case ERROR:
                    Bukkit.getLogger().severe(line);
                    break;
            }
        }
    }


    @Override
    public void run() {
        if (!waitingForTestResult) {
            if (!hasNext()) {
                currentTest.cleanup();
                printBanner(ChatColor.GREEN + "Success!");
                plugin.destroyTestRunner();
                return;
            }

            if (!runNext()) {
                waitingForTestResult = true;
            }
        } else {
            if (currentTest.isDone() && (player == null || !currentTest.hasConfirmation())) {
                waitingForTestResult = false;
            }
        }

    }

    public void repeat() {
        currentTest.cleanup();
        runTest(currentTest);
    }

    public Location getSpawn() {
        return spawn.clone();
    }
}
