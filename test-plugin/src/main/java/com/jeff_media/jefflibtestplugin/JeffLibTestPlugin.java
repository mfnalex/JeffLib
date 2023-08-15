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

import co.aikar.commands.PaperCommandManager;
import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.Tasks;
import com.jeff_media.jefflibtestplugin.commands.JeffLibTestCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class JeffLibTestPlugin extends JavaPlugin {

    @Getter
    private static JeffLibTestPlugin instance;
    @Getter
    private TestRunner testRunner;

    {
        instance = this;
        JeffLib.init(this);
    }

    public World getFlatWorld() {
        WorldCreator creator = new WorldCreator("jefflib-test-world");
        creator.generateStructures(false);
        creator.type(WorldType.FLAT);
        World world = Bukkit.createWorld(creator);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        return world;
    }


    @Override
    public void onEnable() {
        PaperCommandManager acf = new PaperCommandManager(this);
        acf.registerCommand(new JeffLibTestCommand(this));

        Tasks.nextTick(() -> createTestRunner(null).run());

    }

    public TestRunner createTestRunner(Player player) {
        if (this.testRunner != null) {
            this.testRunner.cleanup();
        }

        try {
            this.testRunner = new TestRunner(this, player);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return this.testRunner;
    }

    public void destroyTestRunner() {
        if (this.testRunner != null) {
            this.testRunner.cleanup();
        }
        this.testRunner = null;
    }
}
