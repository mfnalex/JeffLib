/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.pluginhooks;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

@UtilityClass
public class SlimefunUtils {

    private static Class<?> slimefunItemClass;
    private static Class<?> slimefunBackpackClass;
    private static Method getByItemMethod;

    static {
        try {
            initNewAPI();
        } catch (final Throwable ignored) {
            try {
                initOldAPI();
            } catch (final Throwable ignored2) {

            }
        }
    }

    private static void initOldAPI() throws ClassNotFoundException, NoSuchMethodException {
        slimefunItemClass = Class.forName("me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem");
        slimefunBackpackClass = Class.forName("io.github.thebusybiscuit.slimefun4.implementation.items.backpacks.SlimefunBackpack");
        getByItemMethod = slimefunItemClass.getMethod("getByItem", ItemStack.class);
    }

    private static void initNewAPI() throws ClassNotFoundException, NoSuchMethodException {
        slimefunItemClass = Class.forName("io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem");
        slimefunBackpackClass = Class.forName("io.github.thebusybiscuit.slimefun4.implementation.items.backpacks.SlimefunBackpack");
        getByItemMethod = slimefunItemClass.getMethod("getByItem", ItemStack.class);
    }

    public static boolean isSlimefunBackpack(@Nonnull final ItemStack item) {

        if (Bukkit.getPluginManager().getPlugin("Slimefun") == null) {
            return false;
        }

        try {
            final Object result = getByItemMethod.invoke(null, item);
            if (result == null) return false;
            return slimefunBackpackClass.isInstance(result);
        } catch (final Throwable ignored) {
            return false;
        }

    }
}
