package com.jeff_media.jefflib;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

@UtilityClass
public class DebugUtils {

    /**
     * Prints a Map's content to console. Example:
     * <pre>
     * firstKey -> firstValue
     * secondKey -> secondValue
     * </pre>
     */
    public static void print(final Map<?,?> map) {
        for(final Map.Entry<?,?> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" -> " + entry.getValue());
        }
    }

    public static void print(final ItemStack[] items) {
        System.out.println("ItemStack[" + items.length + "]");
        for(final ItemStack item : items) {
            if(item == null) continue;
            System.out.println(" - " + item);
        }
    }

    public enum ShellType {
        NAUTILUS_SHELL(Material.NAUTILUS_SHELL),
        TURTLE_SHELL(Material.TURTLE_HELMET),
        SHULKER_SHELL(Material.SHULKER_SHELL);

        @Getter private final Material material;
        private final ItemStack itemstack;

        ShellType(Material material) {
            this.material = material;
            this.itemstack = new ItemStack(material);
        }

        public ItemStack getItemstack() {
            return itemstack;
        }
    }
    public static ItemStack getShell(ShellType type) {
        return type.getItemstack().clone();
    }

    public static void print(final Collection<?> collection) {
        collection.forEach(System.out::println);
    }

    public static final class Events {
        private static final Logger logger = JeffLib.getPlugin().getLogger();
        public static void debug(final InventoryClickEvent event) {
            final Inventory top = event.getView().getTopInventory();
            final Inventory bottom = event.getView().getBottomInventory();
            logger.warning("============================================================");
            logger.warning("Top inventory holder: " + (top.getHolder() == null ? null : top.getHolder().getClass().getName()));
            logger.warning("Bottom inventory holder: " + (bottom.getHolder() == null ? null : bottom.getHolder().getClass().getName()));
            logger.warning("InventoryAction: " + event.getAction().name());
            logger.warning("Clicked inventory holder: " + (event.getClickedInventory() == null ? null : (event.getClickedInventory().getHolder() == null ? null : event.getClickedInventory().getHolder().getClass().getName())));
            logger.warning("Current Item: " + event.getCurrentItem());
            logger.warning("Cursor: " + event.getCursor());
            logger.warning("Hotbar Button: " + event.getHotbarButton());
            logger.warning("Raw Slot: " + event.getRawSlot());
            logger.warning("Slot: " + event.getSlot());
            logger.warning("Slot Type: " + event.getSlotType().name());
            logger.warning("Left Click: " + event.isLeftClick());
            logger.warning("Right Click: " + event.isRightClick());
            logger.warning("Shift Click: " + event.isShiftClick());
            logger.warning("============================================================");
        }

    }
}
