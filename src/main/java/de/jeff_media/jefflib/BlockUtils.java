package de.jeff_media.jefflib;

import org.bukkit.block.Block;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockUtils {

    public static List<Map.Entry<String, String>> getBlockDataAsEntries(final Block block) {
        List<Map.Entry<String,String>> list = new ArrayList<>();

        String split[] = block.getBlockData().getAsString().split("\\[");
        if(split.length==1) {
            return list;
        }
        String info = split[1];
        info = info.substring(0,info.length()-1);
        String[] entries = info.split(",");
        for(String entry : entries) {
            String key = entry.split("=")[0];
            String value = entry.split("=")[1];
            list.add(new AbstractMap.SimpleEntry<>(key, value));
        }
        return list;
    }

}
