package de.jeff_media.jefflib.pluginhooks;

import de.jeff_media.jefflib.data.WorldBoundingBox;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import de.jeff_media.jefflib.internal.PluginUtils;
import de.jeff_media.jefflib.internal.blackhole.WorldEditHandler;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

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
    public static WorldBoundingBox getSelection(final Player player) throws MissingPluginException {

        try {
            return WorldEditHandler.getSelection(player);
        } catch (final Throwable throwable) {
            throw new MissingPluginException("WorldEdit");
        }
    }
}
