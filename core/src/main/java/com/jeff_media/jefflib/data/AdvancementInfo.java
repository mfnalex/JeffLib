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

package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.annotations.NMS;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;

import java.lang.reflect.InvocationTargetException;

/**
 * Provides information about an advancement.
 *
 * @author CroaBeast
 * @nms
 * @see <a href="https://www.spigotmc.org/threads/getting-advancement-info-1-12-1-18.537707/">SpigotMC Thread</a>
 */
@NMS
public class AdvancementInfo {

    private static final Class<?> CLASS_CRAFTADVANCEMENT;
    private static final int MC_VERSION;


    static {
        Class<?> tmp = null;
        try {
            tmp = getNMSClass("org.bukkit.craftbukkit", "advancement.CraftAdvancement", true);
        } catch (final Exception ignored) {
        }
        if (tmp == null) throw new NMSNotSupportedException();
        CLASS_CRAFTADVANCEMENT = tmp;

        MC_VERSION = McVersion.current().getMinor();
    }

    private final Advancement adv;
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private String frameType;

    /**
     * Creates a new instance of AdvancementInfo.
     * @param adv The advancement to get the information from.
     */
    public AdvancementInfo(final Advancement adv) {
        this.adv = adv;
        registerKeys();
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

    private static Class<?> getNMSClass(final String start, final String name, final boolean hasVersion) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName((start != null ? start : "net.minecraft.server") + (hasVersion ? "." + version : "") + "." + name);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static ConfigurationSection fromMap(final Map<String, Object> map) {
//        final ConfigurationSection section = new MemoryConfiguration();
//        for (final Map.Entry<String, Object> entry : map.entrySet()) {
//            section.set(entry.getKey(), entry.getValue());
//        }
//        return section;
//    }

    private void registerKeys() {

        final Object craftAdv = CLASS_CRAFTADVANCEMENT.cast(adv);
        final Object advHandle = getObject(CLASS_CRAFTADVANCEMENT, craftAdv, "getHandle");
        if (advHandle == null) return;

        final Object craftDisplay = getObject(advHandle, "c");
        if (craftDisplay == null) return;

        final Object frameType = getObject(craftDisplay, "e");
        final Object chatComponentTitle = getObject(craftDisplay, "a");
        final Object chatComponentDesc = getObject(craftDisplay, "b");
        if (frameType == null || chatComponentTitle == null || chatComponentDesc == null) return;

        final Class<?> chatClass = MC_VERSION >= 17 ? getNMSClass("net.minecraft.network.chat", "IChatBaseComponent", false) : getNMSClass(null, "IChatBaseComponent", true);
        if (chatClass == null) return;

        final Object title = getObject(chatClass, chatComponentTitle, "getString");
        final Object description = getObject(chatClass, chatComponentDesc, "getString");
        if (title == null || description == null) return;

        this.frameType = frameType.toString();
        this.title = title.toString();
        this.description = description.toString();
    }
}
