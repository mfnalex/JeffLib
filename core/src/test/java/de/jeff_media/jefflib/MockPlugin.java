package de.jeff_media.jefflib;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class MockPlugin extends JavaPlugin implements Listener {

    public MockPlugin() {
        super();
    }

    public MockPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File datafolder, File file) {
        super(loader,description,datafolder,file);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        System.out.println("BlockPlaceEvent");
        System.out.println(event.getBlockPlaced());
        System.out.println(event.getItemInHand());
    }
}
