package de.jeff_media.jefflib.internal.nms;

import org.bukkit.entity.Player;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NMSHandler {

    //void updateMap(@NotNull final MapView map);

    void playTotemAnimation(@NotNull final Player player);

}
