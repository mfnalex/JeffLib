package com.jefflib.jefflibtestplugin;

import co.aikar.commands.PaperCommandManager;
import com.jefflib.jefflibtestplugin.commands.JeffLibTestCommand;
import com.jefflib.jefflibtestplugin.commands.SwingCommand;
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
        acf.registerCommand(new SwingCommand());

        createTestRunner(null).run();

    }

    public TestRunner createTestRunner(Player player) {
        if(this.testRunner != null) {
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
        if(this.testRunner != null) {
            this.testRunner.cleanup();
        }
        this.testRunner = null;
    }
}
