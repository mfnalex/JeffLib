package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.WorldBoundingBox;
import de.jeff_media.jefflib.exceptions.MissingWorldEditException;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

public class WorldEditUtils {

    @Nullable
    public static WorldBoundingBox getSelection(Player player) throws MissingWorldEditException {
            try {
                com.sk89q.worldedit.bukkit.BukkitPlayer actor = com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(player);
                com.sk89q.worldedit.session.SessionManager manager = com.sk89q.worldedit.WorldEdit.getInstance().getSessionManager();
                com.sk89q.worldedit.LocalSession session = manager.get(actor);
                com.sk89q.worldedit.world.World selectionWorld = session.getSelectionWorld();
                com.sk89q.worldedit.regions.Region region = session.getSelection(selectionWorld);
                Block max = com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(player.getWorld(),region.getMaximumPoint()).getBlock();
                Block min = com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(player.getWorld(),region.getMinimumPoint()).getBlock();
                return new WorldBoundingBox(player.getWorld(), BoundingBox.of(min, max));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new MissingWorldEditException();
            }
        }
}
