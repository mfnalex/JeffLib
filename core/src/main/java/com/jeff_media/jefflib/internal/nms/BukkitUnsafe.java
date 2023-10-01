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

package com.jeff_media.jefflib.internal.nms;

import com.google.common.collect.Multimap;
import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.NMS;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.UnsafeValues;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * Represents Bukkit's {@link UnsafeValues} class with all version-dependant methods from org.bukkit.craftbukkit.&lt;version&gt;.util.CraftMagicNumbers included
 */
@SuppressWarnings("deprecation")
public interface BukkitUnsafe {

    /**
     * Returns the BukkitUnsafe instance
     *
     * @return The BukkitUnsafe instance
     */
    static BukkitUnsafe getInstance() {
        return JeffLib.getNMSHandler().getUnsafe();
    }

    /**
     * Turns a Material into a legacy Material
     *
     * @param material The Material to convert
     * @return The legacy Material
     */
    default Material toLegacy(final Material material) {
        return Bukkit.getUnsafe().toLegacy(material);
    }

    /**
     * Turns a legacy Material into a Material
     *
     * @param material The legacy Material to convert
     * @return The Material
     */
    default Material fromLegacy(final Material material) {
        return Bukkit.getUnsafe().fromLegacy(material);
    }

    /**
     * Turns a legacy MaterialData into a Material
     *
     * @param material The legacy MaterialData to convert
     * @return The Material
     */
    default Material fromLegacy(final MaterialData material) {
        return Bukkit.getUnsafe().fromLegacy(material);
    }

    /**
     * Turns a legacy MaterialData into a Material
     *
     * @param material     The legacy MaterialData to convert
     * @param itemPriority Whether to prioritize items over blocks if the MaterialData is ambiguous
     * @return The Material
     */
    default Material fromLegacy(final MaterialData material, final boolean itemPriority) {
        return Bukkit.getUnsafe().fromLegacy(material, itemPriority);
    }

    /**
     * Turns a legacy Material with data into a BlockData
     *
     * @param material The legacy Material to convert
     * @param data     The legacy data value
     * @return The Material
     */
    default BlockData fromLegacy(final Material material, final byte data) {
        return Bukkit.getUnsafe().fromLegacy(material, data);
    }

    /**
     * Turns a material name from an older version into a Material
     *
     * @param material The old material name
     * @param version  The "world version" / "DataFixer version" the material name is from
     * @return The Material
     */
    default Material getMaterial(final String material, final int version) {
        return Bukkit.getUnsafe().getMaterial(material, version);
    }

    /**
     * Gets the current "world version" / "DataFixer version"
     *
     * @return The current "world version" / "DataFixer version"
     */
    default int getDataVersion() {
        return Bukkit.getUnsafe().getDataVersion();
    }

    /**
     * Applies the given "Mojangson" NBT data to the given ItemStack (similar to how the vanilla /give command works)
     *
     * @param stack     The ItemStack to modify
     * @param arguments The "Mojangson" NBT data to apply
     * @return The modified ItemStack
     */
    default ItemStack modifyItemStack(final ItemStack stack, final String arguments) {
        return Bukkit.getUnsafe().modifyItemStack(stack, arguments);
    }

    /**
     * Checks if the server supports the API version defined in the given {@link PluginDescriptionFile}, and enables Legacy Material support if it doesn't define an API version.
     *
     * @param pdf The PluginDescriptionFile to check
     * @throws InvalidPluginException If the API version defined in the PluginDescriptionFile is not supported by the server
     */
    default void checkSupported(final PluginDescriptionFile pdf) throws InvalidPluginException {
        Bukkit.getUnsafe().checkSupported(pdf);
    }

    /**
     * Translates a class between legacy and modern constants (biome names, material names, etc), e.g. "CACTUS_GREEN" &lt;-&gt; "GREEN_DYE" or "JUNGLE_EDGE" &lt;-&gt; "SPARSE_JUNGLE"
     *
     * @param pdf   The PluginDescriptionFile of the plugin to translate the class for
     * @param path  The path/name of the class to translate
     * @param clazz The bytecode of the class to translate
     * @return The translated bytecode, that then gets thrown at the outdated plugin's class loader
     */
    default byte[] processClass(final PluginDescriptionFile pdf, final String path, final byte[] clazz) {
        return Bukkit.getUnsafe().processClass(pdf, path, clazz);
    }

    /**
     * Loads and registers an advancement from a JSON string. This registers the advancement
     * both in NMS and Bukkit.
     *
     * @param key         The NamespacedKey under which to register this advancement
     * @param advancement The JSON string containing the advancement data
     * @return The loaded Bukkit advancement
     */
    default Advancement loadAdvancement(final NamespacedKey key, final String advancement) {
        return Bukkit.getUnsafe().loadAdvancement(key, advancement);
    }

    /**
     * Removes an advancement's file from Bukkit's datapack folder. It will NOT immediately unload it from the server.
     *
     * @param key The NamespacedKey of the advancement to remove
     * @return Whether the advancement's file was successfully removed
     */
    default boolean removeAdvancement(final NamespacedKey key) {
        return Bukkit.getUnsafe().removeAdvancement(key);
    }

    /**
     * Gets the default attribute modifiers for the given material and equipment slot
     *
     * @param material The material to get the default attribute modifiers for
     * @param slot     The equipment slot to get the default attribute modifiers for
     * @return The default attribute modifiers for the given material and equipment slot
     */
    default Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(final Material material, final EquipmentSlot slot) {
        return Bukkit.getUnsafe().getDefaultAttributeModifiers(material, slot);
    }

    default CreativeCategory getCreativeCategory(final Material material) {
        return Bukkit.getUnsafe().getCreativeCategory(material);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    default Object getNMSBlockStateBlock(final MaterialData material) {
        return getNMSBlockState(material.getItemType(), material.getData());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object getNMSBlockState(Material material, byte data);

    MaterialData getMaterialFromNMSBlockState(Object nmsBlockState);

    Object getNMSItem(Material material, short data);

    MaterialData getMaterialDataFromNMSItem(Object nmsItem);

    Material getMaterialFromNMSBlock(Object nmsBlock);

    Material getMaterialFromNMSItem(Object nmsItem);

    /**
     * Turns an NMS Fluid into a Bukkit Fluid
     *
     * @param nmsFluid The NMS Fluid
     * @return The Bukkit Fluid
     * @nms 1.16.2-1.20.1
     */
    @NMS("1.16.2")
    @Deprecated
    Object getFluidFromNMSFluid(Object nmsFluid);

    Object getNMSItemFromMaterial(Material material);

    Object getNMSBlockFromMaterial(Material material);

    /**
     * @nms 1.16.2-1.20.1
     */
    @NMS("1.16.2")
    @Deprecated
    Object getNMSFluid(Object fluid);

    Object getNMSResourceLocation(Material material);

    byte NMSBlockStateToLegacyData(Object nmsBlockState);

    String getMappingsVersion();

    File getBukkitDataPackFolder();

    boolean isLegacy(PluginDescriptionFile file);

    final class NBT {
        public static final int TAG_END = 0;
        public static final int TAG_BYTE = 1;
        public static final int TAG_SHORT = 2;
        public static final int TAG_INT = 3;
        public static final int TAG_LONG = 4;
        public static final int TAG_FLOAT = 5;
        public static final int TAG_DOUBLE = 6;
        public static final int TAG_BYTE_ARRAY = 7;
        public static final int TAG_STRING = 8;
        public static final int TAG_LIST = 9;
        public static final int TAG_COMPOUND = 10;
        public static final int TAG_INT_ARRAY = 11;
        public static final int TAG_ANY_NUMBER = 99;

    }
}



