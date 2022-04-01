package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.exceptions.ConflictingEnchantmentException;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Enchantment related methods
 */
@UtilityClass
public final class EnchantmentUtils {

    /**
     * Registers a custom enchantment. You should not do that, but instead rely on PDC tags to identify your custom enchantments.
     * The Enchantment class wasn't meant to be expanded by plugin enchantments, and your custom enchantment will also be lost
     * when playes use an enchantment table on the item.
     * @param enchantment Enchantment to register
     */
    @Deprecated
    public void registerEnchantment(final Enchantment enchantment) throws ConflictingEnchantmentException {
        try {
            final Field fieldAcceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            fieldAcceptingNew.setAccessible(true);
            fieldAcceptingNew.set(null, true);
            fieldAcceptingNew.setAccessible(false);

            Enchantment.registerEnchantment(enchantment);
        } catch (final NoSuchFieldException | IllegalAccessException exception) {
            throw new ConflictingEnchantmentException(exception.getMessage());
        }
    }

    /**
     * Returns the level of an enchantment on the given item, or 0 if it's not enchanted with this enchantment
     *
     * @param item        Item to check
     * @param enchantment Enchantment to check
     * @return Level of the enchantmant, or 0 if not present
     */
    public static int getLevel(final ItemStack item, final Enchantment enchantment) {
        if (!item.hasItemMeta()) {
            return 0;
        }
        final ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if (meta.hasEnchant(enchantment)) {
            return meta.getEnchantLevel(enchantment);
        }
        return 0;
    }

    /**
     * Gets an enchant by name (case-insensitive), e.g. "unbreaking" or "fortune".
     * @param name Name of the enchantment (case-insensitive)
     * @return The enchantment of the specified name
     */
    @Nullable
    public static Enchantment getByName(@NotNull final String name) {
        for(final Enchantment enchant : Enchantment.values()) {
            if(enchant.getKey().getKey().equalsIgnoreCase(name)) {
                return enchant;
            }
        }
        return null;
    }

    /**
     * Parses Enchantments including levels from a {@link ConfigurationSection}. The given ConfigurationSection should look like this:
     * <pre>
     * efficiency: 2 # Efficiency Level 2
     * fortune: 1 # Fortune Level 1
     * </pre>
     *
     * @param section ConfigurationSection to parse
     * @return A Map&lt;Enchantment,Integer> containing the given enchantments mapped to their given levels
     */
    public static @NotNull Map<Enchantment,Integer> fromConfigurationSection(final ConfigurationSection section) {
        final Map<Enchantment,Integer> map = new HashMap<>();
        for(final String enchantName : section.getKeys(false)) {
            final Enchantment enchant = getByName(enchantName);
            final int level = section.getInt(enchantName);
            if(enchant != null) {
                map.put(enchant, level);
            }
        }
        return map;
    }

}
