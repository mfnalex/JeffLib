package de.jeff_media.jefflib.customblock;

import de.jeff_media.jefflib.exceptions.InvalidBlockDataException;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import de.jeff_media.jefflib.internal.InternalOnly;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nullable;

@UtilityClass
@InternalOnly
public class ItemsAdderWrapper {

    @Nullable
    static BlockData getFromId(String id) throws MissingPluginException {
        if(Bukkit.getPluginManager().getPlugin("ItemsAdder") == null) {
            throw new MissingPluginException("ItemsAdder");
        }
        return ItemsAdderHandler.getFromId(id);
    }

}
