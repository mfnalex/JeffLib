package de.jeff_media.jefflib.customblock;

import de.jeff_media.jefflib.JeffLib;
import de.jeff_media.jefflib.ProfileUtils;
import de.jeff_media.jefflib.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HeadsHandler {

    private static OfflinePlayer getOfflinePlayer(String name) {
        final Player player = Bukkit.getPlayerExact(name);
        if(player != null) return player;
        return Bukkit.getOfflinePlayer(name);
    }

    public static CustomBlock getFromString(@NotNull String string, final @Nullable OfflinePlayer player) {
        final BlockData data = Material.PLAYER_HEAD.createBlockData();

        if(player != null) {
            JeffLib.debug("HeadHandler->Given player: " + player.getUniqueId());
            string = string.replace("{player}", player.getUniqueId().toString());
            JeffLib.debug("HeadHandler->translated: " + string);
        }

        if(ProfileUtils.isValidAccountName(string)) {
            JeffLib.debug("HeadHandler->Valid Account Name");
            return fromPlayerName(string, data);
        }

        if(ProfileUtils.isValidUUID(string)) {
            JeffLib.debug("HeadHandler->Valid UUID");
            return fromUUID(ProfileUtils.getUUIDFromString(string), data);
        }

        JeffLib.debug("HeadHandler->fromBase64");
        return fromBase64(string, data, player);
    }

    private static CustomBlock fromBase64(String string, BlockData data, OfflinePlayer player) {
        return new CustomBlock(CustomBlock.Provider.VANILLA, data, block -> {
            final BlockState state = block.getState();
            if(state instanceof Skull) {
                SkullUtils.setBase64Texture(block, string);
            }
        });
    }

    private static CustomBlock fromUUID(@NotNull final UUID uuid, @NotNull final BlockData data) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return fromOfflinePlayer(player, data);
    }

    @NotNull
    private static CustomBlock fromOfflinePlayer(@NotNull final OfflinePlayer player, @NotNull final BlockData data) {
        //final ItemStack head = SkullUtils.getHead(player);
        return new CustomBlock(CustomBlock.Provider.VANILLA, data, block -> {
            final BlockState state = block.getState();
            if (state instanceof Skull) {
                final Skull skull = (Skull) state;
                skull.setOwningPlayer(player);
                skull.update();
            }
        });
    }

    @NotNull
    private static CustomBlock fromPlayerName(@NotNull final String string, @NotNull final BlockData data) {
        final OfflinePlayer player = getOfflinePlayer(string);
        return fromOfflinePlayer(player, data);
    }

}
