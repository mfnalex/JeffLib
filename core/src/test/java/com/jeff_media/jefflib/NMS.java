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

package com.jeff_media.jefflib;


import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.world.level.Level;
import org.bukkit.event.Listener;

import org.bukkit.event.block.Action;
import org.bukkit.plugin.java.JavaPlugin;


public class NMS extends JavaPlugin implements Listener {

    private static final IntSet RIGHT_CLICK_ORDINALS = IntSet.of(Arrays.stream(Action.values()).filter(action -> action.name().contains("RIGHT_CLICK")).mapToInt(Enum::ordinal).toArray());
    private static final IntSet LEFT_CLICK_ORDINALS = IntSet.of(Arrays.stream(Action.values()).filter(action -> action.name().contains("LEFT_CLICK")).mapToInt(Enum::ordinal).toArray());

    public static boolean isRightClick(Action action) {
        return RIGHT_CLICK_ORDINALS.contains(action.ordinal());
    }

    public static boolean isLeftClick(Action action) {
        return LEFT_CLICK_ORDINALS.contains(action.ordinal());
    }

    {

    }

}


