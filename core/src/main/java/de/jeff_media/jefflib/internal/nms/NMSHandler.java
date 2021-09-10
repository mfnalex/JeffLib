package de.jeff_media.jefflib.internal.nms;

import com.mojang.authlib.GameProfile;
import de.jeff_media.jefflib.internal.InternalOnly;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityComparator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
@InternalOnly
public interface NMSHandler {

    //void updateMap(@NotNull final MapView map);

    void playTotemAnimation(@NotNull final Player player);

    void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile);

    /*int getComparatorOutput(final Block block);

    void setComparatorOutput(final Block block, final int value);*/

}
