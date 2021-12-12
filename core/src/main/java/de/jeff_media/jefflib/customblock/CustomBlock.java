package de.jeff_media.jefflib.customblock;

import com.google.common.base.Enums;
import de.jeff_media.jefflib.exceptions.InvalidBlockDataException;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.Locale;

public class CustomBlock {

    public enum Provider {
        VANILLA, ORAXEN, ITEMSADDER, FURNITUREENGINE
    }

    @Getter private final BlockData blockData;
    @Getter private final Provider provider;

    public static CustomBlock fromString(String id) throws MissingPluginException, InvalidBlockDataException {
        if(id.startsWith("minecraft:")) return fromBlockDataString(id);
        if(id.startsWith("itemsadder:")) return fromItemsAdderId(id.split(":")[1]);
        //if(id.startsWith("oraxen:")) return fromOraxenId(id.split(":")[1]);
        Material material = Enums.getIfPresent(Material.class, id.toUpperCase(Locale.ROOT)).orNull();
        if(material == null) throw new InvalidBlockDataException("Could not find Material with ID " + id);
        return new CustomBlock(Provider.VANILLA,Bukkit.createBlockData(material));
    }

    public static CustomBlock fromBlockDataString(String blockDataString) {
        return new CustomBlock(Provider.VANILLA, Bukkit.createBlockData(blockDataString));
    }

    public void place(Block block) {
        block.setType(blockData.getMaterial());
        block.setBlockData(blockData);
    }

    private static CustomBlock fromItemsAdderId(String id) throws MissingPluginException {
        BlockData blockData = ItemsAdderWrapper.getFromId(id);
        if(blockData == null) {
            throw new InvalidBlockDataException("Could not find ItemsAdder block with ID " + id);
        }
        return new CustomBlock(CustomBlock.Provider.ITEMSADDER, blockData);
    }

    public Material getType() {
        return blockData.getMaterial();
    }

    public CustomBlock(final Provider provider, final BlockData blockData) {
        this.blockData = blockData;
        this.provider = provider;
    }
}
