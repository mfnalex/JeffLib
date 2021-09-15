package de.jeff_media.jefflib.internal.nms.v1_17_R1;

import com.mojang.authlib.GameProfile;
import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.chunk.Chunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
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
        final PlayerConnection playerConnection = entityPlayer.b;
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
