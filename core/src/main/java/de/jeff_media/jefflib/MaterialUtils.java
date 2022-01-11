package de.jeff_media.jefflib;

import com.google.gson.Gson;
import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Material related methods
 */
@UtilityClass
public final class MaterialUtils {

    /**
     * Returns the "vanilla namespaced" name for this material, e.g. "block.minecraft.dirt" or "item.minecraft.diamond_hoe"
     *
     * @param mat Material
     * @return "Vanilla namespaced" name
     */
    public static String getMinecraftNamespacedName(final Material mat) {
        if (mat.isBlock()) {
            return "block.minecraft." + mat.name().toLowerCase(Locale.ROOT);
        } else {
            return "item.minecraft." + mat.name().toLowerCase(Locale.ROOT);
        }
    }

    /**
     * Reads Minecraft's client translation files into a map
     *
     * @param minecraftTranslationFile Minecraft's client translation file
     * @return Map mapping all Materials to their translated name
     */
    public static Map<Material, String> getTranslatedMaterialMap(final File minecraftTranslationFile) {
        final Gson gson = new Gson();
        final Map<Material, String> map = new HashMap<>();
        for (final Material mat : Material.values()) {
            map.put(mat, getNiceMaterialName(mat));
        }
        try {
            final Map<?,?> tmpMap = gson.fromJson(new FileReader(minecraftTranslationFile), Map.class);
            for (final Material mat : Material.values()) {
                map.put(mat, (String) tmpMap.get(getMinecraftNamespacedName(mat)));
            }
        } catch (final FileNotFoundException ignored) {
            //ignored.printStackTrace();
        }
        return map;
    }

    /**
     * Turns Material names into a nicer name. E.g. DIAMOND_PICKAXE will return "Diamond Pickaxe"
     *
     * @param mat The Material
     * @return Human readable name
     */
    public static String getNiceMaterialName(final Material mat) {
        final StringBuilder builder = new StringBuilder();
        final Iterator<String> iterator = Arrays.stream(mat.name().split("_")).iterator();
        while (iterator.hasNext()) {
            builder.append(WordUtils.upperCaseFirstLetterOnly(iterator.next()));
            if (iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Sets this material's max stack size
     * @param material Material to change
     * @param maxStackSize new max stack size
     */
    public static void setMaxStackSize(final Material material, final int maxStackSize) {
        NMSNotSupportedException.check();
        ReflUtils.setField(Material.class, material, "maxStack",maxStackSize);
        JeffLib.getNMSHandler().getMaterialHandler().setMaxStackSize(material, maxStackSize);
    }

}
