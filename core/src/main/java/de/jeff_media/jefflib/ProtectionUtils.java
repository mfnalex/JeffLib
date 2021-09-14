package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.ShadowPlayer;
import de.jeff_media.jefflib.exceptions.MissingPluginException;
import de.jeff_media.jefflib.pluginhooks.WorldGuardUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ProtectionUtils {

    public static boolean canBreak(@NotNull final Player player, @NotNull final Block block) {
        final BlockBreakEvent event = new BlockBreakEvent(block, new ShadowPlayer(player));
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Block block) {
        final BlockPlaceEvent event = new BlockPlaceEvent(block,block.getState(),block.getRelative(BlockFace.DOWN),player.getInventory().getItemInMainHand(),new ShadowPlayer(player),true,EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Block block) {
        final PlayerInteractEvent event = new PlayerInteractEvent(new ShadowPlayer(player), Action.RIGHT_CLICK_BLOCK, player.getInventory().getItemInMainHand(), block, BlockFace.UP,EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled() || event.useInteractedBlock() == Event.Result.DENY) {
            return false;
        }
        return true;
    }

    /*public static boolean canBreak(@NotNull final Player player, @NotNull final Block block) {
        final boolean alreadyMuted = ChatUtils.isDeaf(player);
        ChatUtils.setDeaf(player, true);

        try {
            if (!WorldGuardUtils.canBreak(player, block.getLocation())) {
                ChatUtils.setDeaf(player, alreadyMuted);
                return false;
            }
        } catch (final MissingPluginException ignored) {

        }

        final BlockBreakEvent event = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(event);
        try {
            ChatUtils.setDeaf(player, alreadyMuted);
        } catch (final Throwable ignored) {

        }
        return !event.isCancelled();
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Block block) {
        final boolean alreadyMuted = ChatUtils.isDeaf(player);
        ChatUtils.setDeaf(player, true);
        try {
            if (!WorldGuardUtils.canPlace(player, block.getLocation())) {
                ChatUtils.setDeaf(player, alreadyMuted);
                return false;
            }
        } catch (final MissingPluginException ignored) {

        }

        final BlockPlaceEvent event = new BlockPlaceEvent(block, block.getState(), block.getRelative(BlockFace.SELF), new ItemStack(block.getType()),player,true, EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        try {
            ChatUtils.setDeaf(player, alreadyMuted);
        } catch (final Throwable ignored) {

        }
        return !event.isCancelled();
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Block block) {
        final boolean alreadyMuted = ChatUtils.isDeaf(player);
        ChatUtils.setDeaf(player, true);
        try {
            if (!WorldGuardUtils.canInteract(player, block.getLocation())) {
                ChatUtils.setDeaf(player, alreadyMuted);
                return false;
            }
        } catch (final MissingPluginException ignored) {

        }

        final PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK,player.getInventory().getItemInMainHand(),block,BlockFace.SELF, EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        try {
            ChatUtils.setDeaf(player, alreadyMuted);
        } catch (final Throwable ignored) {

        }
        if(event.useInteractedBlock() == Event.Result.DENY || event.isCancelled()) {
            return false;
        }
        return true;
    }

    public static boolean canBreak(@NotNull final Player player, @NotNull final Location location) {
        return canBreak(player, location.getBlock());
    }

    public static boolean canPlace(@NotNull final Player player, @NotNull final Location location) {
        return canPlace(player, location.getBlock());
    }

    public static boolean canInteract(@NotNull final Player player, @NotNull final Location location) {
        return canInteract(player, location.getBlock());
    }*/
}
