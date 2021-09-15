package de.jeff_media.jefflib.internal.nms.v1_16_R3;

import com.mojang.authlib.GameProfile;
import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NMSHandler implements AbstractNMSHandler {

    @Override
    public Pair<String, String> getBiomeName(@NotNull Location location) {
        return NMSBiomeUtils.getBiomeName(location);
    }

    @Override
    public void playTotemAnimation(final Player player) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Packet<PacketListenerPlayOut> packet = new PacketPlayOutEntityStatus(entityPlayer, (byte) 35);
        final PlayerConnection playerConnection = entityPlayer.playerConnection;
        playerConnection.sendPacket(packet);
    }

    @Override
    public void setHeadTexture(final Block block, final GameProfile gameProfile) {
        final World world = ((CraftWorld) block.getWorld()).getHandle();
        final BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        final TileEntitySkull skull = (TileEntitySkull) world.getTileEntity(blockPosition);
        skull.setGameProfile(gameProfile);
    }


}
