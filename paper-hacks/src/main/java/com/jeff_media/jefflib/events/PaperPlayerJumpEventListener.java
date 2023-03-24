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
