package com.jeff_media.jefflib.internal.nms.v1_18_R2;

import com.jeff_media.jefflib.ReflUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Fluid;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class BukkitUnsafe implements com.jeff_media.jefflib.internal.nms.BukkitUnsafe {
    public static final com.jeff_media.jefflib.internal.nms.BukkitUnsafe INSTANCE = new BukkitUnsafe();

    @Override
    public Object getNMSBlockState(final Material material, final byte data) {
        return CraftMagicNumbers.getBlock(material, data);
    }

    @Override
    public MaterialData getMaterialFromNMSBlockState(final Object nmsBlockState) {
        return CraftMagicNumbers.getMaterial((BlockState) nmsBlockState);
    }

    @Override
    public Object getNMSItem(final Material material, final short data) {
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
    public Object getNMSItemFromMaterial(final Material material) {
        return CraftMagicNumbers.getItem(material);
    }

    @Override
    public Object getNMSBlockFromMaterial(final Material material) {
        return CraftMagicNumbers.getBlock(material);
    }

    @Override
    public Object getNMSFluid(final Object fluid) {
        return CraftMagicNumbers.getFluid((Fluid)fluid);
    }

    @Override
    public Object getNMSResourceLocation(final Material material) {
        return CraftMagicNumbers.key(material);
    }

    @Override
    public byte NMSBlockStateToLegacyData(final Object nmsBlockState) {
        return CraftMagicNumbers.toLegacyData((BlockState) nmsBlockState);
    }

    @Override
    public String getMappingsVersion() {
        return ((CraftMagicNumbers)CraftMagicNumbers.INSTANCE).getMappingsVersion();
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