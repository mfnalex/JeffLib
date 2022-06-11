package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.ShadowPlayer;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import de.jeff_media.jefflib.pluginhooks.WorldGuardUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import javax.annotation.Nonnull;

@UtilityClass
public class ProtectionUtils {

    public static boolean canBreak(@Nonnull final Player player, @Nonnull final Block block) {
        return canBreak(player, block, false);
    }

    public static boolean canBreak(@Nonnull final Player player, @Nonnull final Block block, final boolean mute) {
        try {
            if(!WorldGuardUtils.canBreak(player, block.getLocation())) return false;
        } catch (final MissingPluginException ignored) {

        }
        final BlockBreakEvent event = new BlockBreakEvent(block, mute ? new ShadowPlayer(player) : player);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canPlace(@Nonnull final Player player, @Nonnull final Block block) {
        return canPlace(player, block, false);
    }

    public static boolean canPlace(@Nonnull final Player player, @Nonnull final Block block, final boolean mute) {
        try {
            if(!WorldGuardUtils.canPlace(player, block.getLocation())) return false;
        } catch (final MissingPluginException ignored) {

        }
        final BlockPlaceEvent event = new BlockPlaceEvent(block,block.getState(),block.getRelative(BlockFace.DOWN),player.getInventory().getItemInMainHand(),mute ? new ShadowPlayer(player) : player,true,EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canInteract(@Nonnull final Player player, @Nonnull final Block block) {
        return canInteract(player, block, false);
    }

    public static boolean canInteract(@Nonnull final Player player, @Nonnull final Block block, final boolean mute) {
        try {
            if(!WorldGuardUtils.canInteract(player, block.getLocation())) return false;
        } catch (final MissingPluginException ignored) {

        }
        final PlayerInteractEvent event = new PlayerInteractEvent(mute ? new ShadowPlayer(player) : player, Action.RIGHT_CLICK_BLOCK, player.getInventory().getItemInMainHand(), block, BlockFace.UP,EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        //noinspection deprecation
        return !event.isCancelled() && event.useInteractedBlock() != Event.Result.DENY;
    }
}
