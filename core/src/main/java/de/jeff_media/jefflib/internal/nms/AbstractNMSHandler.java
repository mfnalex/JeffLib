package de.jeff_media.jefflib.internal.nms;

import com.mojang.authlib.GameProfile;
import de.jeff_media.jefflib.data.Hologram;
import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.internal.InternalOnly;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@InternalOnly
public interface AbstractNMSHandler {

    //void updateMap(@NotNull final MapView map);

    void changeNMSEntityName(@NotNull Object entity, @NotNull String name);

    Object createHologram(@NotNull Location location, @NotNull String line, @NotNull Hologram.Type type);

    void showEntityToPlayer(@NotNull Object entity, @NotNull Player player);

    void hideEntityFromPlayer(@NotNull Object entity, @NotNull Player player);

    void sendPacket(@NotNull final Player player, @NotNull final Object packet);

    Pair<String,String> getBiomeName(@NotNull final Location location);

    void playTotemAnimation(@NotNull final Player player);

    void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile);

    NBTItem getItem(ItemStack item);

}
