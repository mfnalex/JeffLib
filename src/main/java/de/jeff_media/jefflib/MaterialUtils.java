package de.jeff_media.jefflib;

import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Material related methods
 */
public class MaterialUtils {

    /**
     * Returns the "vanilla namespaced" name for this material, e.g. "block.minecraft.dirt" or "item.minecraft.diamond_hoe"
     * @param mat Material
     * @return "Vanilla namespaced" name
     */
    public static String getMinecraftNamespacedName(Material mat) {
        if(mat.isBlock()) {
            return "block.minecraft." + mat.name().toLowerCase(Locale.ROOT);
        } else {
            return "item.minecraft." + mat.name().toLowerCase(Locale.ROOT);
        }
    }

    /**
     * Reads Minecraft's client translation files into a map
     * @param minecraftTranslationFile Minecraft's client translation file
     * @return Map mapping all Materials to their translated name
     */
    public static Map<Material,String> getTranslatedMaterialMap(File minecraftTranslationFile) {
        Gson gson = new Gson();
        Map<Material,String> map = new HashMap<>();
        for(Material mat : Material.values()) {
            map.put(mat, getNiceMaterialName(mat));
        }
        try {
            Map tmpMap = gson.fromJson(new FileReader(minecraftTranslationFile), Map.class);
            for(Material mat : Material.values()) {
                map.put(mat, (String) tmpMap.get(getMinecraftNamespacedName(mat)));
            }
        } catch (FileNotFoundException ignored) {
            //ignored.printStackTrace();
        }
        return map;
    }

    /**
     * Turns Material names into a nicer name. E.g. DIAMOND_PICKAXE will return "Diamond Pickaxe"
     * @param mat The Material
     * @return Human readable name
     */
    public static String getNiceMaterialName(Material mat) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = Arrays.stream(mat.name().split("_")).iterator();
        while(iterator.hasNext()) {
            builder.append(WordUtils.upperCaseFirstLetterOnly(iterator.next()));
            if(iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }

}
