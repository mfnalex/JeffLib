package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.MissingPluginException;
import de.jeff_media.jefflib.internal.blackhole.ChatMuteHandler;
import de.jeff_media.jefflib.pluginhooks.WorldGuardUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ProtectionUtils {

    public static boolean canBuildHere(@NotNull final Player player, @NotNull final Block block) {

        final boolean alreadyMuted = ChatMuteHandler.isMuted(player);
        ChatMuteHandler.mute(player, true);

        try {
            if (!WorldGuardUtils.canBuild(player, block.getLocation())) {
                ChatMuteHandler.mute(player, alreadyMuted);
                return false;
            }
        } catch (final MissingPluginException ignored) {

        }

        final BlockPlaceEvent event = new BlockPlaceEvent(block, block.getState(), block.getRelative(BlockFace.SELF), new ItemStack(block.getType()),player,true, EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent(event);
        ChatMuteHandler.mute(player, alreadyMuted);
        return !event.isCancelled();
    }

    public static boolean canBuildHere(@NotNull final Player player, @NotNull final Location location) {
        return canBuildHere(player, location.getBlock());
    }
}
