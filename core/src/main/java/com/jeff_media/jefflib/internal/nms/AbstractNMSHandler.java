package com.jeff_media.jefflib.internal.nms;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.CustomGoalExecutor;
import com.jeff_media.jefflib.ai.PathfinderGoal;
import com.jeff_media.jefflib.ai.TemptGoal;
import com.mojang.authlib.GameProfile;
import com.jeff_media.jefflib.data.Hologram;
import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.internal.annotations.Internal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.stream.Stream;

@Internal
public interface AbstractNMSHandler {

    //void updateMap(@Nonnull final MapView map);

    AbstractNMSMaterialHandler getMaterialHandler();

    AbstractNMSBlockHandler getBlockHandler();

    void changeNMSEntityName(@Nonnull Object entity, @Nonnull String name);

    Object createHologram(@Nonnull Location location, @Nonnull String line, @Nonnull Hologram.Type type);

    void showEntityToPlayer(@Nonnull Object entity, @Nonnull Player player);

    void hideEntityFromPlayer(@Nonnull Object entity, @Nonnull Player player);

    void sendPacket(@Nonnull final Player player, @Nonnull final Object packet);

    Pair<String,String> getBiomeName(@Nonnull final Location location);

    void playTotemAnimation(@Nonnull final Player player);

    void setHeadTexture(@Nonnull final Block block, @Nonnull final GameProfile gameProfile);

    String itemStackToJson(@Nonnull final ItemStack itemStack);

    void setFullTimeWithoutTimeSkipEvent(@Nonnull final World world, final long time, final boolean notifyPlayers);

	double[] getTps();

	int getItemStackSizeInBytes(ItemStack itemStack) throws IOException;

	String getDefaultWorldName();

    TemptGoal createTemptGoal(LivingEntity entity, Stream<Material> materials, double speed, boolean canScare);

    boolean addGoal(LivingEntity entity, PathfinderGoal goal, int priority);

    boolean moveTo(LivingEntity entity, double x, double y, double z, double speed);

    boolean isPathfinderMob(Entity entity);

    boolean isServerRunnning();

    CustomGoalExecutor getCustomGoalExecutor(CustomGoal customGoal, LivingEntity entity);
}
