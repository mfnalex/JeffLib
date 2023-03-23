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

package com.jeff_media.jefflib;

import com.google.gson.Gson;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.nms.AbstractNMSTranslationKeyProvider;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Material related methods
 */
@UtilityClass
public class MaterialUtils {

    private static final Method GET_BLOCK_TRANSLATION_KEY_METHOD;
    private static final Method GET_ITEM_TRANSLATION_KEY_METHOD;

    static {
        Method tmpGetBlockTranslationKeyMethod = null;
        Method tmpGetItemTranslationKeyMethod = null;
        if(ServerUtils.hasTranslationKeyProvider()) {
            try {
                tmpGetBlockTranslationKeyMethod = Material.class.getMethod("getBlockTranslationKey");
                tmpGetItemTranslationKeyMethod = Material.class.getMethod("getItemTranslationKey");
            } catch (ReflectiveOperationException ignored) {

            }
        }
        GET_BLOCK_TRANSLATION_KEY_METHOD = tmpGetBlockTranslationKeyMethod;
        GET_ITEM_TRANSLATION_KEY_METHOD = tmpGetItemTranslationKeyMethod;
    }


    /**
     * Reads Minecraft's client translation files into a map
     *
     * @param minecraftTranslationFile Minecraft's client translation file
     * @return Map mapping all Materials to their translated name
     */
    public static Map<Material, String> getTranslatedMaterialMap(final File minecraftTranslationFile) {
        final Gson gson = new Gson();
        final Map<Material, String> map = new HashMap<>();
        for (final Material mat : Material.values()) {
            map.put(mat, getNiceMaterialName(mat));
        }
        try {
            final Map<?, ?> tmpMap = gson.fromJson(new FileReader(minecraftTranslationFile), Map.class);
            for (final Material mat : Material.values()) {
                map.put(mat, (String) tmpMap.get(getMinecraftNamespacedName(mat)));
            }
        } catch (final FileNotFoundException ignored) {
            //ignored.printStackTrace();
        }
        return map;
    }

    /**
     * Turns Material names into a nicer name. E.g. DIAMOND_PICKAXE will return "Diamond Pickaxe"
     *
     * @param mat The Material
     * @return Human readable name
     */
    public static String getNiceMaterialName(final Material mat) {
        final StringBuilder builder = new StringBuilder();
        final Iterator<String> iterator = Arrays.stream(mat.name().split("_")).iterator();
        while (iterator.hasNext()) {
            builder.append(WordUtils.upperCaseFirstLetterOnly(iterator.next()));
            if (iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Returns the "vanilla namespaced" name for this material, e.g. "block.minecraft.dirt" or "item.minecraft.diamond_hoe"
     *
     * @param mat Material
     * @return "Vanilla namespaced" name
     */
    public static String getMinecraftNamespacedName(final Material mat) {
        if (mat.isBlock()) {
            return "block.minecraft." + mat.name().toLowerCase(Locale.ROOT);
        } else {
            return "item.minecraft." + mat.name().toLowerCase(Locale.ROOT);
        }
    }

    /**
     * Sets this material's max stack size
     *
     * @param material     Material to change
     * @param maxStackSize new max stack size
     * @nms
     */
    @NMS
    public static void setMaxStackSize(final Material material, final int maxStackSize) {
        if (!material.isItem()) return; // Can't set the amount for unobtainable items like WATER
        ReflUtils.setFieldValue(Material.class, material, "maxStack", maxStackSize);
        JeffLib.getNMSHandler().getMaterialHandler().setMaxStackSize(material, maxStackSize);
    }

    /**
     * Get the translation key of the item or block associated with this material. If this material has both an item and a block form, the item form is used.
     * @nms Requires NMS in versions below latest 1.19.3
     * @see #getBlockTranslationKey(Material)
     * @return the translation key of the item or block associated with this material. If this material has both an item and a block form, the item form is used.
     */
    @NMS
    @NotNull
    public static String getTranslationKey(@NotNull final Material material) {
        String result;
        if(material.isItem()) {
            result = getItemTranslationKey(material);
        } else {
            result = getBlockTranslationKey(material);
        }
        if(result == null) {
            throw new IllegalStateException("Material " + material + " has neither a block nor item translation key");
        }
        return result;
    }

    /**
     * Get the translation key of the block associated with this material. If this material does not have any block form, like DIAMOND_PICKAXE, it returns null
     * @nms Requires NMS in versions below latest 1.19.3
     * @see #getItemTranslationKey(Material)
     * @return The translation key of the block associated with this material, or null if this material does not have a block form
     */
    @NMS
    @Nullable
    public static String getBlockTranslationKey(@NotNull final Material material) {
        if(ServerUtils.hasTranslationKeyProvider()) {
            try {
                return (String) GET_BLOCK_TRANSLATION_KEY_METHOD.invoke(material);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            if(JeffLib.getNMSHandler() instanceof AbstractNMSTranslationKeyProvider) {
                return ((AbstractNMSTranslationKeyProvider) JeffLib.getNMSHandler()).getBlockTranslationKey(material);
            } else {
                throw new NMSNotSupportedException("This version of NMS does not support getting the translation key of an EntityType");
            }
        }
    }

    /**
     * Get the translation key of the item associated with this material. If this material does not have any item form, like NETHER_PORTAL_FRAME, it returns null
     * @nms Requires NMS in versions below latest 1.19.3
     * @see #getBlockTranslationKey(Material)
     * @return The translation key of the item associated with this material, or null if this item does not have a block form
     */
    @NMS
    @Nullable
    public static String getItemTranslationKey(@NotNull final Material material) {
        if(ServerUtils.hasTranslationKeyProvider()) {
            try {
                return (String) GET_ITEM_TRANSLATION_KEY_METHOD.invoke(material);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            if(JeffLib.getNMSHandler() instanceof AbstractNMSTranslationKeyProvider) {
                return ((AbstractNMSTranslationKeyProvider) JeffLib.getNMSHandler()).getItemTranslationKey(material);
            } else {
                throw new NMSNotSupportedException("This version of NMS does not support getting the translation key of an EntityType");
            }
        }
    }



}
