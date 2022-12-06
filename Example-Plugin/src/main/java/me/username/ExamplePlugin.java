package me.username;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    {
        JeffLib.init(this);
    }

    @Override
    public void onEnable() {
        System.out.println("Hello world");
    }

    @Override
    public void onDisable() {
        System.out.println("Goodbye world");
    }
}
