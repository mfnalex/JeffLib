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

package com.jeff_media.jefflib.data;

import com.google.common.base.Enums;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import org.bukkit.Material;

public enum WoodType {

    OAK("OAK"), JUNGLE("JUNGLE"), DARK_OAK("DARK_OAK"), ACACIA("ACACIA"), BIRCH("BIRCH"), SPRUCE("SPRUCE"), CRIMSON("CRIMSON"), WARPED("WARPED"), MANGROVE("MANGROVE");

    @Getter
    @Nonnull
    private final Material button, door, fence, fenceGate, leaves, log, planks, pressurePlate, sapling, sign, slab, stairs, trapdoor, wallSign, wood, pottedSapling, strippedLog, strippedWood;

    @Getter
    @Nullable
    private final Material boat, pottedRoots;

    WoodType(final String type) {
        //System.out.println("CREATING WOODTYPE: " + type);
        boat = getMaterial(type, "BOAT");
        button = getMaterial(type, "BUTTON");
        door = getMaterial(type, "DOOR");
        fence = getMaterial(type, "FENCE");
        fenceGate = getMaterial(type, "FENCE_GATE");
        leaves = getMaterial(type, "LEAVES");
        log = getMaterial(type, "LOG");
        planks = getMaterial(type, "PLANKS");
        pressurePlate = getMaterial(type, "PRESSURE_PLATE");
        sapling = getMaterial(type, "SAPLING");
        sign = getMaterial(type, "SIGN");
        slab = getMaterial(type, "SLAB");
        stairs = getMaterial(type, "STAIRS");
        trapdoor = getMaterial(type, "TRAPDOOR");
        wallSign = getMaterial(type, "WALL_SIGN");
        wood = getMaterial(type, "WOOD");
        pottedSapling = getMaterial("POTTED", type, "SAPLING");
        strippedLog = getMaterial("STRIPPED", type, "LOG");
        strippedWood = getMaterial("STRIPPED", type, "WOOD");
        pottedRoots = getMaterial("POTTED", type, "ROOTS");
    }

    @Nullable
    public static WoodType fromMaterial(final Material type) {
        for (final WoodType wood : values()) {
            if (wood.getBoat() == type) return wood;
            if (wood.getButton() == type) return wood;
            if (wood.getDoor() == type) return wood;
            if (wood.getFence() == type) return wood;
            if (wood.getFenceGate() == type) return wood;
            if (wood.getLeaves() == type) return wood;
            if (wood.getLog() == type) return wood;
            if (wood.getPlanks() == type) return wood;
            if (wood.getPressurePlate() == type) return wood;
            if (wood.getSapling() == type) return wood;
            if (wood.getSign() == type) return wood;
            if (wood.getSlab() == type) return wood;
            if (wood.getStairs() == type) return wood;
            if (wood.getTrapdoor() == type) return wood;
            if (wood.getWallSign() == type) return wood;
            if (wood.getWood() == type) return wood;
            if (wood.getPottedSapling() == type) return wood;
            if (wood.getStrippedLog() == type) return wood;
            if (wood.getStrippedWood() == type) return wood;
            if (wood.getPottedRoots() == type) return wood;
        }
        return null;
    }

    private static boolean isFungus(final String type) {
        switch (type) {
            case "CRIMSON":
            case "WARPED":
                return true;
            default:
                return false;
        }
    }

    public boolean isFungus(final WoodType type) {
        switch (type) {
            case CRIMSON:
            case WARPED:
                return true;
            default:
                return false;
        }
    }

    private Material getMaterial(final String woodType, final String type) {
        return getMaterial(null, woodType, type);
    }

    private Material getMaterial(final String prefix, final String woodType, String type) {
        if (this == MANGROVE) {
            switch (type) {
                case "LEAVES":
                    return Enums.getIfPresent(Material.class, "MANGROVE_ROOTS").orNull();
                case "SAPLING":
                    return Enums.getIfPresent(Material.class, "MANGROVE_PROPAGULE").orNull();
            }
        }
        if (isFungus(woodType)) {
            if (type.equals("LOG")) type = "STEM";
            if (type.equals("WOOD")) type = "HYPHAE";
            if (type.equals("SAPLING")) type = "FUNGUS";
        }
        if (type.equals("LEAVES") && isFungus(woodType)) {
            if (woodType.equals("CRIMSON")) return Material.NETHER_WART_BLOCK;
            if (woodType.equals("WARPED")) return Material.WARPED_WART_BLOCK;
        }
        final String name = (prefix == null ? "" : prefix) + ((prefix == null || prefix.length() == 0) ? "" : "_") + woodType + "_" + type;
        return Enums.getIfPresent(Material.class, name).orNull();
    }

}
