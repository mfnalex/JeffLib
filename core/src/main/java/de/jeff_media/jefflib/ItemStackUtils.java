package de.jeff_media.jefflib;

import com.google.common.base.Enums;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import javax.annotation.Nullable;

/**
 * ItemStack related methods
 */
@UtilityClass
public final class ItemStackUtils {

    public static boolean isNullOrEmpty(final ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() == 1;
    }

    public ItemStack fromConfigurationSection(@NotNull final ConfigurationSection config) {
        return fromConfigurationSection(config, new HashMap<>());
    }

    private String replaceInString(String string, final Map<String,String> placeholders) {
        for(final Map.Entry<String, String> entry : placeholders.entrySet()) {
            if(entry.getKey()==null ||entry.getValue()==null) continue;
            string = string.replace(entry.getKey(), entry.getValue());
        }
        return string;
    }

    private List<String> replaceInString(final List<String> strings, final HashMap<String,String> placeholders) {
        for(int i = 0; i < strings.size(); i++) {
            strings.set(i, replaceInString(strings.get(i),placeholders));
        }
        return strings;
    }

    public static ItemStack fromConfigurationSection(@NotNull final ConfigurationSection config, final HashMap<String,String> placeholders) {
        final String materialName = config.getString("material","BARRIER").toUpperCase(Locale.ROOT);

        int amount = 1;
        if(config.isSet("amount")) {
            if(config.isInt("amount")) {
                amount = config.getInt("amount");
            }
        }

        final ItemStack item;

        if(materialName.equalsIgnoreCase("PLAYER_HEAD") && config.isSet("base64") && config.isString("base64")) {
            item = SkullUtils.getHead(Objects.requireNonNull(config.getString("base64")));
            //System.out.println("Creating custom head with base64" + config.getString("base64"));
        } else {
            item = new ItemStack(Enums.getIfPresent(Material.class, materialName).or(Material.BARRIER), amount);
        }

        List<String> lore = new ArrayList<>();
        if(config.isSet("lore")) {
            if(config.isString("lore")) {
                lore.add(TextUtils.format(replaceInString(config.getString("lore"),placeholders)));
            } else {
                lore = TextUtils.format(replaceInString(config.getStringList("lore"),placeholders),null);
            }
        }

        String name = null;
        if(config.isSet("display-name")) {
            name = TextUtils.format(replaceInString(config.getString("display-name"),placeholders));
        }

        Integer modelData = null;
        if(config.isInt("custom-model-data")) {
            modelData = config.getInt("custom-model-data");
        }

        final int damage = config.getInt("damage",0);

        final ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setLore(lore);
        meta.setDisplayName(name);
        meta.setCustomModelData(modelData);

        if(config.isConfigurationSection("enchantments")) {
            for(final String key : Objects.requireNonNull(config.getConfigurationSection("enchantments")).getKeys(false)) {
                final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key));
                if(enchantment == null) {
                    throw new IllegalArgumentException("Unknown enchantment: " + key);
                }
                final int level = Objects.requireNonNull(config.getConfigurationSection("enchantments")).getInt(key,1);
                meta.addEnchant(enchantment, level,true);
            }
        }

        if(meta instanceof Damageable) {
            ((Damageable)meta).setDamage(damage);
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Merges ItemStacks when possible
     *
     * @param items ItemStacks to merge
     * @return merged ItemStacks
     */
    public static List<ItemStack> mergeItemStacks(ItemStack... items) {
        HashMap<Integer, ItemStack> overflow = null;
        items = getNonNullItems(items);
        final List<ItemStack> mergedItems = new ArrayList<>();
        Inventory inventory;

        do {
            inventory = Bukkit.createInventory(null, 54);
            overflow = inventory.addItem(overflow == null ? items : overflow.values().toArray(new ItemStack[0]));
            mergedItems.addAll(Arrays.asList(inventory.getContents()));
        } while (!overflow.isEmpty());

        return mergedItems;
    }

    /**
     * Returns an array of all given ItemStacks that are neither null nor AIR
     *
     * @param items ItemStacks to check
     * @return All given ItemStacks that are neither null nor air
     */
    public static ItemStack[] getNonNullItems(final ItemStack... items) {
        final ArrayList<ItemStack> nonNullItems = new ArrayList<>();
        for (final ItemStack item : items) {
            if (!isNullOrEmpty(item)) nonNullItems.add(item);
        }
        return nonNullItems.toArray(new ItemStack[0]);
    }

    public static void setDisplayName(@NotNull final ItemStack item, @NotNull final String name) {
        final ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(name);
        item.setItemMeta(meta);
    }


    /**
     * Returns a damaged ItemStack by specified amount
     * @param amount damage amount to be applied
     * @param item ItemStack to be damaged
     * @param player Player who damaged the item
     * @return Damaged ItemStack
     */
    public static ItemStack damageItem(int amount,ItemStack item,@Nullable Player player){
        item = item.clone();
        ItemMeta meta = item.getItemMeta();
        if(!(meta instanceof Damageable) || amount < 0) return item;
        int m = item.getEnchantmentLevel(Enchantment.DURABILITY);
        int k = 0;
        for (int l = 0; m > 0 && l < amount; l++) {
            if (JeffLib.getRandom().nextInt(m +1) > 0){
                k++; 
            }
        }  
        amount -= k;
        if(player != null){
            PlayerItemDamageEvent damageEvent = new PlayerItemDamageEvent(player, item, amount);
            Bukkit.getServer().getPluginManager().callEvent(damageEvent);
            if(amount != damageEvent.getDamage() || damageEvent.isCancelled()){
                damageEvent.getPlayer().updateInventory();
            }
            else if(damageEvent.isCancelled()){
                return item;
            }
            amount = damageEvent.getDamage();

        }
        if (amount <= 0)
            return item; 
        
        Damageable damageable = (Damageable) meta;
        damageable.setDamage(damageable.getDamage()+amount);
        item.setItemMeta(meta);    
        return item;
    }

}