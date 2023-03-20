package com.jeff_media.jefflib.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PaperPlayerJumpEventListener implements Listener {

    @EventHandler
    public void onJump(com.destroystokyo.paper.event.player.PlayerJumpEvent paperEvent) {
        PlayerJumpEvent ownEvent = new PlayerJumpEvent(paperEvent.getPlayer(), paperEvent.getFrom(), paperEvent.getTo());
        Bukkit.getPluginManager().callEvent(ownEvent);
        if (ownEvent.isCancelled()) {
            paperEvent.setCancelled(true);
        }
    }

}
