package com.jeff_media.jefflib.internal.nms.v1_16_R3;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.CustomGoalExecutor;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMob;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public final class NMS {

    public static WorldServer getLevel(final World world) {
        return ((CraftWorld) world).getHandle();
    }

    public static BlockPosition getBlockPos(final Block block) {
        return ((CraftBlock) block).getPosition();
    }

    public static Entity toNms(final org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static org.bukkit.entity.Entity toBukkit(final Entity entity) {
        return entity.getBukkitEntity();
    }

    public static EntityLiving toNms(final org.bukkit.entity.LivingEntity entity) {
        return ((CraftLivingEntity) entity).getHandle();
    }

    public static org.bukkit.entity.LivingEntity toBukkit(final EntityLiving entity) {
        return (org.bukkit.entity.LivingEntity) entity.getBukkitEntity();
    }

    public static RecipeItemStack ingredient(final Stream<Material> set) {
        return RecipeItemStack.a(set.map(mat -> new ItemStack(CraftMagicNumbers.getItem(mat))));
    }

    @Nonnull
    public static EntityCreature asPathfinder(final org.bukkit.entity.Creature bukkitEntity) {
        return ((CraftCreature) bukkitEntity).getHandle();
    }

    @Nonnull
    public static EntityInsentient asMob(final org.bukkit.entity.Mob bukkitEntity) {
        return ((CraftMob) bukkitEntity).getHandle();
    }

    public static DedicatedServer getDedicatedServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static BlockPosition toNms(final BlockVector pos) {
        return new BlockPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    public static BlockVector toBukkit(@Nullable final BlockPosition targetPos) {
        if (targetPos == null) {
            return null;
        }
        return new BlockVector(targetPos.getX(), targetPos.getY(), targetPos.getZ());
    }

    public static Vec3D toNms(final Vector vector) {
        return new Vec3D(vector.getX(), vector.getY(), vector.getZ());
    }

    public static Vector toBukkit(final Vec3D vec) {
        return new Vector(vec.x, vec.y, vec.z);
    }

    public static PathfinderGoal toNms(final com.jeff_media.jefflib.ai.PathfinderGoal pathfinderGoal) {
        if(pathfinderGoal instanceof PathfinderGoal) {
            return (PathfinderGoal) pathfinderGoal;
        } else if(pathfinderGoal instanceof CustomGoal) {
            final CustomGoalExecutor customGoalExecutor = ((CustomGoal)pathfinderGoal).getExecutor();
            if(customGoalExecutor instanceof PathfinderGoal) {
                return (PathfinderGoal) customGoalExecutor;
            }
        }
        throw new IllegalArgumentException("Not a valid Goal: " + pathfinderGoal.getClass().getName() + ". For custom goals, extend " + CustomGoal.class.getName());
    }

}
