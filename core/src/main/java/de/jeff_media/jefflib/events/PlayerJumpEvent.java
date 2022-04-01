package de.jeff_media.jefflib.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerJumpEvent extends PlayerMoveEvent {
    public PlayerJumpEvent(@NotNull Player player, @NotNull Location from, @Nullable Location to) {
        super(player, from, to);
    }
}
