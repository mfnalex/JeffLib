package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.ShadowPlayer;
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
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ProtectionUtils {

    public static boolean canBreak(@NotNull final Player player, @NotNull final Block block) {
        return canBreak(player, block, true);
    }

    public static boolean canBreak(@NotNull final Player player, @NotNull final Block block, final boolean mute) {
        final BlockBreakEvent event = new BlockBreakEvent(block, mute ? new ShadowPlayer(player) : player);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Block block) {
        return canPlace(player, block, true);
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Block block, final boolean mute) {
        final BlockPlaceEvent event = new BlockPlaceEvent(block,block.getState(),block.getRelative(BlockFace.DOWN),player.getInventory().getItemInMainHand(),mute ? new ShadowPlayer(player) : player,true,EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Block block) {
        return canInteract(player, block, true);
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Block block, final boolean mute) {
        final PlayerInteractEvent event = new PlayerInteractEvent(mute ? new ShadowPlayer(player) : player, Action.RIGHT_CLICK_BLOCK, player.getInventory().getItemInMainHand(), block, BlockFace.UP,EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled() && event.useInteractedBlock() != Event.Result.DENY;
    }
}
