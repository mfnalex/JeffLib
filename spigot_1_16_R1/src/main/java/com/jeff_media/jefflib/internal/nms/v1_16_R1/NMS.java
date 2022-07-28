package com.jeff_media.jefflib.internal.nms.v1_16_R1;

import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R1.util.CraftMagicNumbers;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public final class NMS {

    public static WorldServer getLevel(final World world) {
        return ((CraftWorld)world).getHandle();
    }

    public static BlockPosition getBlockPos(final Block block) {
        return ((CraftBlock)block).getPosition();
    }

    public static Entity toNms(org.bukkit.entity.Entity entity) {
        return ((CraftEntity)entity).getHandle();
    }

    public static org.bukkit.entity.Entity toBukkit(Entity entity) {
        return entity.getBukkitEntity();
    }

    public static EntityLiving toNms(org.bukkit.entity.LivingEntity entity) {
        return ((CraftLivingEntity)entity).getHandle();
    }

    public static org.bukkit.entity.LivingEntity toBukkit(EntityLiving entity) {
        return (org.bukkit.entity.LivingEntity) entity.getBukkitEntity();
    }

    public static RecipeItemStack ingredient(Stream<Material> set) {
        return RecipeItemStack.a(set.map(mat -> new ItemStack(CraftMagicNumbers.getItem(mat))));
    }

    public static @Nullable EntityCreature asPathfinder(final org.bukkit.entity.Entity bukkitEntity) {
        final Entity nmsEntity = NMS.toNms(bukkitEntity);
        if(nmsEntity instanceof EntityCreature) {
            return (EntityCreature) nmsEntity;
        } else {
            return null;
        }
    }

    public static @Nonnull EntityCreature asPathfinderOrThrow(final org.bukkit.entity.Entity bukkitEntity) {
        final Entity nmsEntity = NMS.toNms(bukkitEntity);
        if(nmsEntity instanceof EntityCreature) {
            return (EntityCreature) nmsEntity;
        } else {
            throw new IllegalArgumentException("Given entity is not a PathfinderMob/EntityCreature: " + nmsEntity.getClass().getName() + " (" + bukkitEntity.getClass().getName() + ")");
        }
    }

    public static DedicatedServer getDedicatedServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static BlockPosition toNms(org.bukkit.util.BlockVector pos) {
        return new BlockPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    public static org.bukkit.util.BlockVector toBukkit(BlockPosition targetPos) {
        return new org.bukkit.util.BlockVector(targetPos.getX(), targetPos.getY(), targetPos.getZ());
    }

    public static Vector toBukkit(Vec3D vec) {
        return new Vector(vec.x, vec.y, vec.z);
    }

    public static Vec3D toNms(Vector vec) {
        return new Vec3D(vec.getX(), vec.getY(), vec.getZ());
    }

}
