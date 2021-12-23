package de.jeff_media.jefflib.customblock;

import de.jeff_media.jefflib.internal.InternalOnly;
import dev.lone.itemsadder.api.CustomBlock;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nullable;

@UtilityClass
@InternalOnly
public final class ItemsAdderHandler {

    @Nullable
    static BlockData getFromId(final @NonNull String id) {
        final CustomBlock customBlock = CustomBlock.getInstance(id);
        if(customBlock == null) return null;
        return customBlock.generateBlockData();
    }
}
