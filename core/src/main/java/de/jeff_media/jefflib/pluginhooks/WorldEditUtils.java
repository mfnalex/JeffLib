package de.jeff_media.jefflib.pluginhooks;

import de.jeff_media.jefflib.PluginUtils;
import de.jeff_media.jefflib.data.worldboundingbox.WorldBoundingBox;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

import java.util.List;

/**
 * WorldEdit related methods. Can be safely used even when WorldEdit is not installed, as long as you catch the {@link MissingPluginException}
 */
@UtilityClass
public class WorldEditUtils {

    /**
     * Checks whether WorldEdit is installed and enabled
     *
     * @return true when WorldEdit is installed and enabled, otherwise false
     */
    public static boolean isWorldEditInstalled() {
        return PluginUtils.isInstalledAndEnabled("WorldEdit");
    }

    /**
     * Returns a {@link WorldBoundingBox} of the player's WorldEdit selection, or null if the player doesn't have any or only an incomplete selection.
     * <p>
     * Example usage:
     *
     * <pre>
     * try {
     *     WorldBoundingBox boundingBox = WorldEditUtils.getSelection(player);
     *     if(boundingBox==null) {
     *         player.sendMessage("You don't have any WorldEdit selection, or it is incomplete.");
     *     } else {
     *         player.sendMessage("Your WorldEdit selection: " + boundingBox);
     *     }
     * } catch (MissingPluginException e) {
     *     player.sendMessage("WorldEdit is not installed!");
     * }
     * </pre>
     *
     * @param player Player to check
     * @return {@link WorldBoundingBox} containing the player's WorldEdit selection, or null if the player doesn't have any or only an incomplete selection.
     * @throws MissingPluginException Exception thrown when WorldEdit is not installed
     */
    public static WorldBoundingBox getSelection(final @Nonnull Player player) throws MissingPluginException {
        try {
            return WorldEditHandler.getCuboidSelection(player);
        } catch (final Throwable throwable) {
            throw new MissingPluginException("WorldEdit");
        }
    }

    public static List<Location> getPolygonSelection(final @Nonnull Player player) throws MissingPluginException {
        try {
            return WorldEditHandler.getPolygonSelection(player);
        } catch (final Throwable throwable) {
            throw new MissingPluginException("WorldEdit");
        }
    }
}
