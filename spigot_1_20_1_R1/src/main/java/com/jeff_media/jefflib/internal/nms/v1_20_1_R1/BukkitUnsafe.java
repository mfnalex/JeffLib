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

package com.jeff_media.jefflib.internal.nms.v1_20_1_R1;

import com.jeff_media.jefflib.ReflUtils;
import java.io.File;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Fluid;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginDescriptionFile;

@SuppressWarnings("deprecation")
public class BukkitUnsafe implements com.jeff_media.jefflib.internal.nms.BukkitUnsafe {
    public static final BukkitUnsafe INSTANCE = new BukkitUnsafe();

    @Override
    public BlockState getNMSBlockState(final Material material, final byte data) {
        return CraftMagicNumbers.getBlock(material, data);
    }

    @Override
    public MaterialData getMaterialFromNMSBlockState(final Object nmsBlockState) {
        return CraftMagicNumbers.getMaterial((BlockState) nmsBlockState);
    }

    @Override
    public Item getNMSItem(final Material material, final short data) {
        return CraftMagicNumbers.getItem(material, data);
    }

    @Override
    public MaterialData getMaterialDataFromNMSItem(final Object nmsItem) {
        return CraftMagicNumbers.getMaterialData((Item) nmsItem);
    }

    @Override
    public Material getMaterialFromNMSBlock(final Object nmsBlock) {
        return CraftMagicNumbers.getMaterial((Block) nmsBlock);
    }

    @Override
    public Material getMaterialFromNMSItem(final Object nmsItem) {
        return CraftMagicNumbers.getMaterial((Item) nmsItem);
    }

    @Override
    public Fluid getFluidFromNMSFluid(final Object nmsFluid) {
        return CraftMagicNumbers.getFluid((net.minecraft.world.level.material.Fluid) nmsFluid);
    }

    @Override
    public Item getNMSItemFromMaterial(final Material material) {
        return CraftMagicNumbers.getItem(material);
    }

    @Override
    public Block getNMSBlockFromMaterial(final Material material) {
        return CraftMagicNumbers.getBlock(material);
    }

    @Override
    public net.minecraft.world.level.material.Fluid getNMSFluid(final Object fluid) {
        return CraftMagicNumbers.getFluid((Fluid) fluid);
    }

    @Override
    public ResourceLocation getNMSResourceLocation(final Material material) {
        return CraftMagicNumbers.key(material);
    }

    @Override
    public byte NMSBlockStateToLegacyData(final Object nmsBlockState) {
        return CraftMagicNumbers.toLegacyData((BlockState) nmsBlockState);
    }

    @Override
    public String getMappingsVersion() {
        return ((CraftMagicNumbers) CraftMagicNumbers.INSTANCE).getMappingsVersion();
    }

    @Override
    public File getBukkitDataPackFolder() {
        try {
            return (File) Objects.requireNonNull(ReflUtils.getMethod(CraftMagicNumbers.class, "getBukkitDataPackFolder")).invoke(null);
        } catch (final ReflectiveOperationException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isLegacy(final PluginDescriptionFile file) {
        return CraftMagicNumbers.isLegacy(file);
    }
}
