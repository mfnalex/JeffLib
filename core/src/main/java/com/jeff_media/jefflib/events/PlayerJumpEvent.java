package com.jeff_media.jefflib.events;

import com.google.common.collect.Sets;
import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.NumberUtils;
import com.jeff_media.jefflib.ServerUtils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an event called when a player jumps.
 *
 * On Spigot or CraftBukkit, this event is called through the {@link PlayerMoveEvent}.
 * On Paper or its forks, this event is called through Paper's builtin PlayerJumpEvent.
 */
public class PlayerJumpEvent extends PlayerEvent implements Cancellable {

    /**
     * Registers the listener needed to call this event
     */
    public static void registerListener() {
        Plugin plugin = JeffLib.getPlugin();
        if(ServerUtils.isRunningPaper()) {
            Bukkit.getPluginManager().registerEvents(new PaperListener(), plugin);
        } else {
            Bukkit.getPluginManager().registerEvents(new SpigotListener(), plugin);
        }
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final @Nonnull Location from, to;

    private boolean cancelled;

    public PlayerJumpEvent(@Nonnull Player who, @Nonnull Location from, @Nonnull Location to) {
        super(who);
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Nonnull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public static class PaperListener implements Listener {
        @EventHandler
        public void onJump(com.destroystokyo.paper.event.player.PlayerJumpEvent paperEvent) {
            PlayerJumpEvent ownEvent = new PlayerJumpEvent(paperEvent.getPlayer(), paperEvent.getFrom(), paperEvent.getTo());
            Bukkit.getPluginManager().callEvent(ownEvent);
            if(ownEvent.isCancelled()) {
                paperEvent.setCancelled(true);
            }
        }
    }

    public static class SpigotListener implements Listener {
        private Set<UUID> prevPlayersOnGround = Sets.newHashSet();

        @EventHandler
        public void onMove(PlayerMoveEvent spigotEvent) {
            final Player player = spigotEvent.getPlayer();
            if (player.getVelocity().getY() > 0) {
                double jumpVelocity = 0.42D;
                if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                    jumpVelocity += (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F;
                }
                if (spigotEvent.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                    if (!player.isOnGround() && NumberUtils.isEqual(player.getVelocity().getY(), jumpVelocity)) {
                        final PlayerJumpEvent ownEvent = new PlayerJumpEvent(player, spigotEvent.getFrom(), spigotEvent.getTo());
                        Bukkit.getPluginManager().callEvent(ownEvent);
                        if(ownEvent.isCancelled()) {
                            spigotEvent.setCancelled(true);
                        }
                    }
                }
            }
            if (player.isOnGround()) {
                prevPlayersOnGround.add(player.getUniqueId());
            } else {
                prevPlayersOnGround.remove(player.getUniqueId());
            }
        }
    }


}
