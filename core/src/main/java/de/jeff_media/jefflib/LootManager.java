package de.jeff_media.jefflib;

import com.google.common.base.Enums;
import de.jeff_media.jefflib.data.VariableItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Deprecated
public class LootManager {

    Map<Enum, List<VariableItemStack>> map = new HashMap<>();

    private void add(Enum key, VariableItemStack item) {
        if(map.containsKey(key)) {
            map.get(key).add(item);
        } else {
            List<VariableItemStack> list = new ArrayList<>();
            list.add(item);
            map.put(key, list);
        }
    }

    public static LootManager fromConfigurationSection(final ConfigurationSection config) {
        LootManager lootManager = new LootManager();
        for(String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            VariableItemStack itemStack = VariableItemStack.fromConfigurationSection(section);

            Material matKey = Enums.getIfPresent(Material.class,key.toUpperCase(Locale.ROOT)).orNull();
            EntityType entityKey = Enums.getIfPresent(EntityType.class, key.toUpperCase(Locale.ROOT)).orNull();

            if(matKey != null) {
                lootManager.add(matKey, itemStack);
            } else if(entityKey != null) {
                lootManager.add(entityKey, itemStack);
            }
        }
        return lootManager;
    }

    public List<ItemStack> getDrops(Enum key) {
        List<ItemStack> drops = new ArrayList<>();
        if(map.containsKey(key)) {
            for(VariableItemStack drop : map.get(key)) {
                if(drop.getChance()>=100 || RandomUtils.getDouble(0,100) <= drop.getChance()) {
                    drops.add(drop.getItemStack());
                }
            }
        }
        return drops;
    }

    public List<ItemStack> getDrops(final Entity entity) {
        return getDrops(entity.getType());
    }

    public List<ItemStack> getDrops(final Material mat) { return getDrops((Enum) mat); }

}
