package de.jeff_media.jefflib.customblock;

import com.google.common.base.Enums;
import de.jeff_media.jefflib.JeffLib;
import de.jeff_media.jefflib.exceptions.InvalidBlockDataException;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Consumer;

public class CustomBlock {

    public enum Provider {
        VANILLA, ORAXEN, ITEMSADDER, FURNITUREENGINE

        //private final String pluginName;
    }

    @Getter private final BlockData blockData;
    @Getter private final Provider provider;
    @Getter private final Material type;
    @Getter @Nullable private Consumer<Block> consumer;

    public static CustomBlock fromMaterial(Material material) {
        return new CustomBlock(Provider.VANILLA, Bukkit.createBlockData(material));
    }

    public static CustomBlock fromString(String id) throws MissingPluginException, InvalidBlockDataException {
        return fromString(id, null);
    }

    public static CustomBlock fromString(String id, final @Nullable OfflinePlayer player) throws MissingPluginException, InvalidBlockDataException {

        final String lowercaseId = id.toLowerCase(Locale.ROOT);

        JeffLib.debug("CustomBlock->fromString: " + id);

        // Vanilla Material
        if(!id.contains(":")) {
            final Material material = Enums.getIfPresent(Material.class, id.toUpperCase(Locale.ROOT)).orNull();
            if(material == null) throw new InvalidBlockDataException("Could not find material with ID " + id);
            JeffLib.debug("CustomBlock->Vanilla->Type");
            return new CustomBlock(Provider.VANILLA, Bukkit.createBlockData(material));
        }

        // Vanilla BlockData
        if(lowercaseId.startsWith("minecraft:")) {
            try {
                JeffLib.debug("CustomBlock->Vanilla-BlockData");
                return fromBlockDataString(lowercaseId);
            } catch (IllegalArgumentException exception) {
                throw new InvalidBlockDataException("Could not parse blockdata: " + id);
            }
        }

        if(!id.contains(":")) {
            throw new InvalidBlockDataException("Could not parse blockdata: " + id);
        }
        final String split = id.split(":")[1];

        // Vanilla heads
        if(lowercaseId.startsWith("head:")) {
            JeffLib.debug("CustomBlock->Head->id: " + split);
            return HeadsHandler.getFromString(split, player);
        }

        // ItemsAdder Block
        if(lowercaseId.startsWith("itemsadder:")) {
            JeffLib.debug("CustomBlock->ItemsAdder->id: " + split);
            return fromItemsAdderId(split.toLowerCase(Locale.ROOT));
        }

        // Oraxen Block
        if(lowercaseId.startsWith("oraxen:")) {
            JeffLib.debug("CustomBlock->Oraxen->id: " + split);
            throw new InvalidBlockDataException("Oraxen is currently not supported due to missing features in their API. If you'd like to get oraxen support, please ask them to allow getting BlockData for their custom blocks without having them actually placed into the world.");
        }

        // Fallback
        throw new InvalidBlockDataException("Could not parse blockdata: " + id);
    }

    private static CustomBlock fromBlockDataString(String blockDataString) {
        return new CustomBlock(Provider.VANILLA, Bukkit.createBlockData(blockDataString));
    }

    public void place(Block block) {
        block.setType(blockData.getMaterial());
        block.setBlockData(blockData);
        if(consumer != null) {
            consumer.accept(block);
        }
    }

    private static CustomBlock fromItemsAdderId(String id) throws MissingPluginException, InvalidBlockDataException {
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
        this(provider,blockData, null);
    }

    @Override
    public String toString() {
        return "CustomBlock{" +
                "blockData=" + blockData.getAsString() +
                ", provider=" + provider +
                '}';
    }

    public CustomBlock(final Provider provider, final BlockData blockData, final @Nullable Consumer<Block> consumer) {
        this.blockData = blockData;
        this.provider = provider;
        this.consumer = consumer;
        this.type = blockData.getMaterial();
    }
}
