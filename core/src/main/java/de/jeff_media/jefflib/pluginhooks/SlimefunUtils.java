package de.jeff_media.jefflib.pluginhooks;

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

        if(Bukkit.getPluginManager().getPlugin("Slimefun") == null) {
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
