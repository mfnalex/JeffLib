package com.jeff_media.jefflib;

import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftBoat;

import java.util.concurrent.ThreadLocalRandom;

public class NMS {

    {
        Entity nmsEntity = new Boat(/* World */null, /* Coordinates */0, 0, 0);
        org.bukkit.entity.Entity bukkitEntity = nmsEntity.getBukkitEntity();
        ServerLevel level = ((CraftWorld)Bukkit.getWorld("world")).getHandle();
        level.addFreshEntity(nmsEntity);
    }

}
