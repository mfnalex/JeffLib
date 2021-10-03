package de.jeff_media.jefflib.internal.nms.v1_16_R2;

import com.mojang.authlib.GameProfile;
import de.jeff_media.jefflib.PacketUtils;
import de.jeff_media.jefflib.data.Hologram;
import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftChatMessage;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NMSHandler implements AbstractNMSHandler {

    @Override
    public void changeNMSEntityName(@NotNull Object entity, @NotNull String name) {
        ((Entity) entity).setCustomName(CraftChatMessage.fromString(name)[0]);
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, new PacketPlayOutEntityMetadata(((Entity)entity).getId(),((Entity)entity).getDataWatcher(),true));
        }
    }

    @Override
    public Object createHologram(@NotNull final Location location, final @NotNull String line, @NotNull final Hologram.Type type) {
        final CraftWorld craftWorld = (CraftWorld) location.getWorld();
        final World world = craftWorld.getHandle();
        final IChatBaseComponent baseComponent = CraftChatMessage.fromString(line)[0];
        final Entity entity;
        switch (type) {
            case EFFECTCLOUD:
                entity = new EntityAreaEffectCloud(world, location.getX(), location.getY(), location.getZ());
                final EntityAreaEffectCloud effectCloud = (EntityAreaEffectCloud) entity;
                effectCloud.setRadius(0);
                effectCloud.setWaitTime(0);
                effectCloud.setDuration(Integer.MAX_VALUE);
                break;
            case ARMORSTAND:
            default:
                entity = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());
                final EntityArmorStand armorStand = (EntityArmorStand) entity;
                armorStand.setNoGravity(true);
                armorStand.setInvisible(true);
                armorStand.setMarker(true);
                armorStand.setSmall(true);
        }

        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.setCustomName(baseComponent);
        entity.setCustomNameVisible(true);
        return entity;
    }

    @Override
    public void showEntityToPlayer(@NotNull final Object entity, @NotNull Player player) {
        PacketPlayOutSpawnEntity packetSpawn = new PacketPlayOutSpawnEntity((Entity) entity);
        PacketUtils.sendPacket(player, packetSpawn);

        PacketPlayOutEntityMetadata packetMeta = new PacketPlayOutEntityMetadata(((Entity)entity).getId(), ((Entity)entity).getDataWatcher(), true);
        PacketUtils.sendPacket(player, packetMeta);
    }

    @Override
    public void hideEntityFromPlayer(@NotNull Object entity, @NotNull Player player) {
        PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(((Entity) entity).getId());
        PacketUtils.sendPacket(player, packetDestroy);
    }

    @Override
    public void sendPacket(@NotNull final Player player, @NotNull final Object packet) {
        NMSPacketUtils.sendPacket(player, packet);
    }

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
