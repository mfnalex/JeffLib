package de.jeff_media.jefflib.internal.nms;

import com.mojang.authlib.GameProfile;
import de.jeff_media.jefflib.data.Hologram;
import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.internal.annotations.Internal;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

@Internal
public interface AbstractNMSHandler {

    //void updateMap(@Nonnull final MapView map);

    AbstractNMSMaterialHandler getMaterialHandler();

    AbstractNMSBlockHandler getBlockHandler();

    void changeNMSEntityName(@Nonnull Object entity, @Nonnull String name);

    Object createHologram(@Nonnull Location location, @Nonnull String line, @Nonnull Hologram.Type type);

    void showEntityToPlayer(@Nonnull Object entity, @Nonnull Player player);

    void hideEntityFromPlayer(@Nonnull Object entity, @Nonnull Player player);

    void sendPacket(@Nonnull final Player player, @Nonnull final Object packet);

    Pair<String,String> getBiomeName(@Nonnull final Location location);

    void playTotemAnimation(@Nonnull final Player player);

    void setHeadTexture(@Nonnull final Block block, @Nonnull final GameProfile gameProfile);

}
