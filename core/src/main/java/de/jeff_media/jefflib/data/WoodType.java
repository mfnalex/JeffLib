package de.jeff_media.jefflib.data;

import com.google.common.base.Enums;
import lombok.Getter;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum WoodType {

    OAK("OAK"),
    JUNGLE("JUNGLE"),
    DARK_OAK("DARK_OAK"),
    ACACIA("ACACIA"),
    BIRCH("BIRCH"),
    SPRUCE("SPRUCE"),
    CRIMSON("CRIMSON"),
    WARPED("WARPED");

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

    WoodType(String type) {
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
        pottedSapling = getMaterial("POTTED",type,"SAPLING");
        strippedLog = getMaterial("STRIPPED",type,"LOG");
        strippedWood = getMaterial("STRIPPED",type,"WOOD");
        pottedRoots = getMaterial("POTTED",type,"ROOTS");
        //System.out.println(type + " -> log: " + log);
        //System.out.println(type + " -> wood: " + wood);
    }

    @Nullable
    public static WoodType fromMaterial(Material type) {
        for(WoodType wood : values()) {
            if(wood.getBoat() == type) return wood;
            if(wood.getButton() == type) return wood;
            if(wood.getDoor() == type) return wood;
            if(wood.getFence() == type) return wood;
            if(wood.getFenceGate() == type) return wood;
            if(wood.getLeaves() == type) return wood;
            if(wood.getLog() == type) return wood;
            if(wood.getPlanks() == type) return wood;
            if(wood.getPressurePlate() == type) return wood;
            if(wood.getSapling() == type) return wood;
            if(wood.getSign() == type) return wood;
            if(wood.getSlab() == type) return wood;
            if(wood.getStairs() == type) return wood;
            if(wood.getTrapdoor() == type) return wood;
            if(wood.getWallSign() == type) return wood;
            if(wood.getWood() == type) return wood;
            if(wood.getPottedSapling() == type) return wood;
            if(wood.getStrippedLog() == type) return wood;
            if(wood.getStrippedWood() == type) return wood;
            if(wood.getPottedRoots() == type) return wood;
        }
        return null;
    }

    private boolean isFungus(String type) {
        switch (type) {
            case "CRIMSON":
            case "WARPED":
                return true;
            default:
                return false;
        }
    }

    public boolean isFungus(WoodType type) {
        switch (type) {
            case CRIMSON:
            case WARPED:
                return true;
            default:
                return false;
        }
    }

    private Material getMaterial(String woodType, String type) {
        return getMaterial(null, woodType, type);
    }

    private Material getMaterial(String prefix, String woodType, String type) {
        if(isFungus(woodType)) {
            if(type.equals("LOG")) type = "STEM";
            if(type.equals("WOOD")) type = "HYPHAE";
            if(type.equals("SAPLING")) type = "FUNGUS";
        }
        if(type.equals("LEAVES") && isFungus(woodType)) {
            if(woodType.equals("CRIMSON")) return Material.NETHER_WART_BLOCK;
            if(woodType.equals("WARPED")) return Material.WARPED_WART_BLOCK;
        }
        String name = (prefix==null?"":prefix) + ((prefix == null || prefix.length() == 0) ? "" : "_") + woodType+"_"+type;
        //System.out.println("\n\n=======================================");
        //System.out.println(name);
        Material mat = Enums.getIfPresent(Material.class, name).orNull();
        //System.out.println("Prefix: " + prefix);
        //System.out.println("WoodType: " + woodType);
        //System.out.println("Type: " + type);
        //System.out.println("Result: " + mat);
        return mat;
    }

}
