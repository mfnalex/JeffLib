package com.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NMS extends JavaPlugin implements Listener {

    private final Map<ArmorStand, Player> armorStandMap = new ConcurrentHashMap<>(); // First UUID is the armorstand, second UUID is the player to follow

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(this, this::doStuff, 1, 1);
    }

    private void doStuff() {
        System.out.println("First line of code");
        System.out.println("Second line of code");
        System.out.println("Third line of code");
        System.out.println("Fourth line of code");
        System.out.println("Fifth line of code");
        System.out.println("Sixth line of code");
        System.out.println("Seventh line of code");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ArmorStand armorStand = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), ArmorStand.class);
        armorStandMap.put(armorStand, event.getPlayer());
    }

}
