/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.internal.nms.v1_19_3;

import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.CustomGoalExecutor;
import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftMob;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftMagicNumbers;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NMS {

    public static Level getLevel(final World world) {
        return ((CraftWorld) world).getHandle();
    }

    public static BlockPos getBlockPos(final Block block) {
        return ((CraftBlock) block).getPosition();
    }

    public static Entity toNms(final org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static org.bukkit.entity.Entity toBukkit(final Entity entity) {
        return entity.getBukkitEntity();
    }

    public static LivingEntity toNms(final org.bukkit.entity.LivingEntity entity) {
        return ((CraftLivingEntity) entity).getHandle();
    }

    public static org.bukkit.entity.LivingEntity toBukkit(final LivingEntity entity) {
        return (org.bukkit.entity.LivingEntity) entity.getBukkitEntity();
    }

    public static Ingredient ingredient(final Stream<Material> set) {
        return Ingredient.of(set.map(mat -> new ItemStack(CraftMagicNumbers.getItem(mat))));
    }

    @NotNull
    public static PathfinderMob asPathfinder(final org.bukkit.entity.Creature bukkitEntity) {
        return ((CraftCreature) bukkitEntity).getHandle();
    }

    @NotNull
    public static Mob asMob(final org.bukkit.entity.Mob bukkitEntity) {
        return ((CraftMob) bukkitEntity).getHandle();
    }

    public static DedicatedServer getDedicatedServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static BlockPos toNms(final BlockVector pos) {
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public static BlockVector toBukkit(@Nullable final BlockPos targetPos) {
        if (targetPos == null) {
            return null;
        }
        return new BlockVector(targetPos.getX(), targetPos.getY(), targetPos.getZ());
    }

    public static Vec3 toNms(final Vector vector) {
        return new Vec3(vector.getX(), vector.getY(), vector.getZ());
    }

    public static Vector toBukkit(final Vec3 vec) {
        return new Vector(vec.x, vec.y, vec.z);
    }

    public static Goal toNms(final PathfinderGoal pathfinderGoal) {
        if (pathfinderGoal instanceof Goal) {
            return (Goal) pathfinderGoal;
        } else if (pathfinderGoal instanceof CustomGoal) {
            final CustomGoalExecutor customGoalExecutor = ((CustomGoal) pathfinderGoal).getExecutor();
            if (customGoalExecutor instanceof Goal) {
                return (Goal) customGoalExecutor;
            }
        }
        throw new IllegalArgumentException("Not a valid Goal: " + pathfinderGoal.getClass().getName() + ". For custom goals, extend " + CustomGoal.class.getName());
    }

    public static MinecraftServer getServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static ServerPlayer toNms(final org.bukkit.entity.Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static net.minecraft.world.level.block.Block toNms(final Block block) {
        return ((CraftBlock) block).getNMS().getBlock();
    }

}
