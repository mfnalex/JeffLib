package com.jeff_media.jefflib.internal.nms.v1_17_R1;

import net.minecraft.core.BlockPos;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftMagicNumbers;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public final class NMS {

    public static Level getLevel(final World world) {
        return ((CraftWorld)world).getHandle();
    }

    public static BlockPos getBlockPos(final Block block) {
        return ((CraftBlock)block).getPosition();
    }

    public static Entity toNms(org.bukkit.entity.Entity entity) {
        return ((CraftEntity)entity).getHandle();
    }

    public static org.bukkit.entity.Entity toBukkit(Entity entity) {
        return entity.getBukkitEntity();
    }

    public static LivingEntity toNms(org.bukkit.entity.LivingEntity entity) {
        return ((CraftLivingEntity)entity).getHandle();
    }

    public static org.bukkit.entity.LivingEntity toBukkit(LivingEntity entity) {
        return (org.bukkit.entity.LivingEntity) entity.getBukkitEntity();
    }

    public static Ingredient ingredient(Stream<Material> set) {
        return Ingredient.of(set.map(mat -> new ItemStack(CraftMagicNumbers.getItem(mat))));
    }

    public static @Nullable PathfinderMob asPathfinder(final org.bukkit.entity.Entity bukkitEntity) {
        final Entity nmsEntity = NMS.toNms(bukkitEntity);
        if(nmsEntity instanceof PathfinderMob) {
            return (PathfinderMob) nmsEntity;
        } else {
            return null;
        }
    }

    public static @Nonnull PathfinderMob asPathfinderOrThrow(final org.bukkit.entity.Entity bukkitEntity) {
        final Entity nmsEntity = NMS.toNms(bukkitEntity);
        if(nmsEntity instanceof PathfinderMob) {
            return (PathfinderMob) nmsEntity;
        } else {
            throw new IllegalArgumentException("Given entity is not a PathfinderMob: " + nmsEntity.getClass().getName() + " (" + bukkitEntity.getClass().getName() + ")");
        }
    }

    public static DedicatedServer getDedicatedServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static BlockPos toNms(org.bukkit.util.BlockVector pos) {
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public static org.bukkit.util.BlockVector toBukkit(BlockPos targetPos) {
        return new org.bukkit.util.BlockVector(targetPos.getX(), targetPos.getY(), targetPos.getZ());
    }

    public static Vector toBukkit(Vec3 vec) {
        return new Vector(vec.x, vec.y, vec.z);
    }

    public static Vec3 toNms(Vector vec) {
        return new Vec3(vec.getX(), vec.getY(), vec.getZ());
    }

}
