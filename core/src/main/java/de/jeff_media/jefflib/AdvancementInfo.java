package de.jeff_media.jefflib;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;

import java.lang.reflect.InvocationTargetException;

/**
 * Taken from https://github.com/CroaBeast/AdvancementInfo with permission given by CroaBeast
 */
public class AdvancementInfo {

    private final Advancement adv;

    @Getter private String title;
    @Getter private String description;
    @Getter private String frameType;

    public AdvancementInfo(final Advancement adv) {
        this.adv = adv;
        registerKeys();
    }

    private final int MAJOR_VERSION =
            Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);

    private static Class<?> getNMSClass(final String start, final String name, final boolean hasVersion) {
        final String version = Bukkit.getServer().getClass().getPackage().
                getName().split("\\.")[3];
        try {
            return Class.forName((start != null ? start : "net.minecraft.server" ) +
                    (hasVersion ? "." + version : "") + "." + name);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getObject(final Class<?> clazz, final Object initial, final String method) {
        try {
            return (clazz != null ? clazz : initial.getClass()).getDeclaredMethod(method).invoke(initial);
        } catch (final NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getObject(final Object initial, final String method) {
        return getObject(null, initial, method);
    }

    private void registerKeys() {
        final Class<?> craftClass = getNMSClass("org.bukkit.craftbukkit", "advancement.CraftAdvancement", true);
        if (craftClass == null) return;

        final Object craftAdv = craftClass.cast(adv);
        final Object advHandle = getObject(craftClass, craftAdv, "getHandle");
        if (advHandle == null) return;

        final Object craftDisplay = getObject(advHandle, "c");
        if (craftDisplay == null) return;

        final Object frameType = getObject(craftDisplay, "e");
        final Object chatComponentTitle = getObject(craftDisplay, "a");
        final Object chatComponentDesc = getObject(craftDisplay, "b");
        if (frameType == null || chatComponentTitle == null || chatComponentDesc == null) return;

        final Class<?> chatClass = MAJOR_VERSION >= 17 ?
                getNMSClass("net.minecraft.network.chat", "IChatBaseComponent", false) :
                getNMSClass(null, "IChatBaseComponent", true);
        if (chatClass == null) return;

        final String method = MAJOR_VERSION < 13 ? "toPlainText" : "getString";
        final Object title = getObject(chatClass, chatComponentTitle, method);
        final Object description = getObject(chatClass, chatComponentDesc, method);
        if (title == null || description == null) return;

        this.frameType = frameType.toString();
        this.title = title.toString();
        this.description = description.toString();
    }
}
