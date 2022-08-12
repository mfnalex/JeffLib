package com.jeff_media.jefflib.internal.nms.v1_16_R1;

import com.jeff_media.jefflib.ReflUtils;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import net.minecraft.server.v1_16_R1.Block;
import net.minecraft.server.v1_16_R1.IBlockData;
import net.minecraft.server.v1_16_R1.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.util.CraftMagicNumbers;
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
        return CraftMagicNumbers.getMaterial((IBlockData) nmsBlockState);
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
    public Object getFluidFromNMSFluid(final Object nmsFluid) {
        throw new NMSNotSupportedException("1_16_R1 and older does not support this method");
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
        throw new NMSNotSupportedException("1_16_R1 and older does not support this method");
    }

    @Override
    public Object getNMSResourceLocation(final Material material) {
        return CraftMagicNumbers.key(material);
    }

    @Override
    public byte NMSBlockStateToLegacyData(final Object nmsBlockState) {
        return CraftMagicNumbers.toLegacyData((IBlockData) nmsBlockState);
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
