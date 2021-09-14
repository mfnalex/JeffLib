package de.jeff_media.jefflib.internal.nms;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_17_R1 implements NMSHandler {

    /*@Nullable
    public static TileEntity getTileEntity(Block block) {
        final org.bukkit.World bukkitWorld = block.getWorld();
        final CraftWorld craftWorld = (CraftWorld) bukkitWorld;
        return craftWorld.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
    }

    @Override
    public int getComparatorOutput(final Block block) {
        final TileEntity entity = getTileEntity(block);
        if(!(entity instanceof TileEntityComparator)) {
            throw new IllegalArgumentException("Block is not instance of TileEntityComparator.");
        }
        final TileEntityComparator comparator = (TileEntityComparator) entity;
        return comparator.d();
    }

    @Override
    public void setComparatorOutput(final Block block, final int value) {
        final TileEntity entity = getTileEntity(block);
        if(!(entity instanceof TileEntityComparator)) {
            throw new IllegalArgumentException("Block is not instance of TileEntityComparator.");
        }
        final TileEntityComparator comparator = (TileEntityComparator) entity;
        comparator.a(value);
        comparator.update();
    }*/

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
