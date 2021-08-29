package de.jeff_media.jefflib.internal.blackhole;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.world.World;
import de.jeff_media.jefflib.data.WorldBoundingBox;
import de.jeff_media.jefflib.internal.InternalOnly;
import lombok.experimental.UtilityClass;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

@InternalOnly
@UtilityClass
public final class WorldEditHandler {

    @Nullable
    public static WorldBoundingBox getSelection(final org.bukkit.entity.Player player) {
        try {
            final Player actor = BukkitAdapter.adapt(player);
            final SessionManager manager = WorldEdit.getInstance().getSessionManager();
            final LocalSession session = manager.get(actor);
            final World selectionWorld = session.getSelectionWorld();
            final Region region = session.getSelection(selectionWorld);
            final Block max = BukkitAdapter.adapt(player.getWorld(), region.getMaximumPoint()).getBlock();
            final Block min = BukkitAdapter.adapt(player.getWorld(), region.getMinimumPoint()).getBlock();
            return new WorldBoundingBox(player.getWorld(), BoundingBox.of(min, max));
        } catch (final IncompleteRegionException exception) {
            return null;
        }
    }

}
