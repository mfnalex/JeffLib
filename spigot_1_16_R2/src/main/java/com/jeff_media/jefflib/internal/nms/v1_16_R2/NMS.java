/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.internal.nms.v1_16_R2;

import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.CustomGoalExecutor;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.DedicatedServer;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.ItemStack;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import net.minecraft.server.v1_16_R2.PathfinderGoal;
import net.minecraft.server.v1_16_R2.RecipeItemStack;
import net.minecraft.server.v1_16_R2.Vec3D;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftMob;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftMagicNumbers;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

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

    public static PathfinderGoal toNms(final com.jeff_media.jefflib.ai.goal.PathfinderGoal pathfinderGoal) {
        if (pathfinderGoal instanceof PathfinderGoal) {
            return (PathfinderGoal) pathfinderGoal;
        } else if (pathfinderGoal instanceof CustomGoal) {
            final CustomGoalExecutor customGoalExecutor = ((CustomGoal) pathfinderGoal).getExecutor();
            if (customGoalExecutor instanceof PathfinderGoal) {
                return (PathfinderGoal) customGoalExecutor;
            }
        }
        throw new IllegalArgumentException("Not a valid Goal: " + pathfinderGoal.getClass().getName() + ". For custom goals, extend " + CustomGoal.class.getName());
    }

    public static MinecraftServer getServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static EntityPlayer toNms(final org.bukkit.entity.Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static net.minecraft.server.v1_16_R2.Block toNms(final Block block) {
        return ((CraftBlock) block).getNMS().getBlock();
    }

}
