package de.jeff_media.jefflib.data;

import com.google.common.base.Enums;
import lombok.Getter;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum WoodType {

    OAK, JUNGLE, DARK_OAK, ACACIA, BIRCH, SPRUCE, CRIMSON, WARPED;

    @Getter @NotNull private final Material button,
            door,
            fence,
            fenceGate,
            leaves,
            log,
            planks,
            pressurePlate,
            sapling,
            sign,
            slab,
            stairs,
            trapdoor,
            wallSign,
            wood,
            pottedSapling,
            strippedLog,
            strippedWood;

    @Getter @Nullable private final Material boat,
            pottedRoots;

    WoodType() {
        boat = getMaterial(this, "BOAT");
        button = getMaterial(this, "BUTTON");
        door = getMaterial(this, "DOOR");
        fence = getMaterial(this, "FENCE");
        fenceGate = getMaterial(this, "FENCE_GATE");
        leaves = getMaterial(this, "LEAVES");
        log = getMaterial(this, "LOG");
        planks = getMaterial(this, "PLANKS");
        pressurePlate = getMaterial(this, "PRESSURE_PLATE");
        sapling = getMaterial(this, "SAPLING");
        sign = getMaterial(this, "SIGN");
        slab = getMaterial(this, "SLAB");
        stairs = getMaterial(this, "STAIRS");
        trapdoor = getMaterial(this, "TRAPDOOR");
        wallSign = getMaterial(this, "WALL_SIGN");
        wood = getMaterial(this, "WOOD");
        pottedSapling = getMaterial("POTTED",this,"SAPLING");
        strippedLog = getMaterial("STRIPPED",this,"LOG");
        strippedWood = getMaterial("STRIPPED",this,"WOOD");
        pottedRoots = getMaterial("POTTED",this,"ROOTS");
    }

    private Material getMaterial(WoodType woodType, String type) {
        return getMaterial(null, woodType, type);
    }

    public boolean isFungus() {
        switch (this) {
            case CRIMSON:
            case WARPED:
                return true;
            default: return false;
        }
    }

    private Material getMaterial(String prefix, WoodType woodType, String type) {
        if(isFungus()) {
            if(type.equals("LOG")) type = "STEM";
            if(type.equals("WOOD")) type = "HYPHAE";
            if(type.equals("SAPLING")) type = "FUNGUS";
        }
        if(type.equals("LEAVES") && isFungus()) {
            if(this == CRIMSON) return Material.NETHER_WART_BLOCK;
            if(this == WARPED) return Material.WARPED_WART_BLOCK;
        }
        return Enums.getIfPresent(Material.class, prefix + ((prefix == null || prefix.length() == 0) ? "" : "_") + woodType.name()+"_"+type).orNull();
    }

}
