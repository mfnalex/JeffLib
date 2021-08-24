package de.jeff_media.jefflib;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

// TODO: Check 1.17 compatibility
/**
 * Player head / skull related methods - <b>Might not be 100% compatible with 1.17+ yet</b>
 */
public class SkullUtils {

    /**
     * Gives an already placed skull another skin.
     *
     * @param block Skull
     * @param uuid  UUID of the player
     */
    public static void changeSkullInWorld(final Block block, final UUID uuid) {

        block.setType(Material.PLAYER_HEAD);

        if (!(block.getState() instanceof Skull)) {
            throw new IllegalStateException("Given block is not a skull");
        }

        final Skull state = (Skull) block.getState();

        // Use the player skin's texture
        final OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(uuid);
        state.setOwningPlayer(player);
        state.update();
    }


    // Use a predefined texture

    /**
     * Gives an already placed skull another skin.
     *
     * @param block  Skull
     * @param base64 Base64 encoded skin
     */
    public static void changeSkullInWorld(final Block block, final String base64) {

        block.setType(Material.PLAYER_HEAD);

        if (!(block.getState() instanceof Skull)) {
            throw new IllegalStateException("Given block is not a skull");
        }

        final Skull state = (Skull) block.getState();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));

        try {
            final Object nmsWorld = ReflUtils.getMethodCached(World.class, "getHandle").invoke(block.getWorld());
            final Class<?> blockPositionClass = ReflUtils.getNMSClass("BlockPosition");
            final Class<?> tileEntityClass = ReflUtils.getNMSClass("TileEntitySkull");
            final Constructor<?> blockPositionConstructor = ReflUtils.getConstructorCached(blockPositionClass, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object blockPosition = blockPositionConstructor.newInstance(block.getX(), block.getY(), block.getZ());
            final Method getTileEntityMethod = ReflUtils.getMethodCached(nmsWorld.getClass(), "getTileEntity", blockPositionClass);
            final Object tileEntity = getTileEntityMethod.invoke(nmsWorld, blockPosition);
            final Method setGameProfileMethod = ReflUtils.getMethodCached(tileEntityClass, "setGameProfile", GameProfile.class);
            setGameProfileMethod.invoke(tileEntity, profile);

        } catch (final IllegalArgumentException | IllegalAccessException | SecurityException | InvocationTargetException | InstantiationException e) {
            Bukkit.getLogger().warning("JeffLib: Could not set custom base64 player head.");
        }
    }

    /**
     * Creates an ItemStack of PLAYER_HEAD with the given base64 encoded skin
     *
     * @param base64 Base64 encoded skin
     * @return ItemStack of PLAYER_HEAD using the given skin
     */
    public static ItemStack getHead(final String base64) {

        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta meta = (SkullMeta) head.getItemMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));
        final Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return new ItemStack(Material.PLAYER_HEAD);
        }

        head.setItemMeta(meta);
        return head;
    }

    /**
     * Creates an ItemStack of PLAYER_HEAD with the given player's skin
     *
     * @param uuid UUID of the player
     * @return ItemStack of PLAYER_HEAD with the given player's skin
     */
    public static ItemStack getPlayerHead(final UUID uuid) {
        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta skullMeta = (SkullMeta) (head.hasItemMeta() ? head.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD));
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        head.setItemMeta(skullMeta);
        return head;
    }
}
