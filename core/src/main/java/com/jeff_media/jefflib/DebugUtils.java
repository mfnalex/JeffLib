package com.jeff_media.jefflib;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.Pathfinders;
import com.jeff_media.jefflib.ai.TemptGoal;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.minecraft.world.item.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

@UtilityClass
public class DebugUtils {

    public static void testNms(Player player) {
        JeffLib.enableNMS();

        // NMS version
        String nmsVersion = McVersion.current().getNmsVersion();
        if(nmsVersion == null) throw new IllegalArgumentException("No NMS version defined in mcversions.csv");
        player.sendMessage("NMS Version: " + nmsVersion);

        // Default world name
        String defaultWorldName = WorldUtils.getDefaultWorldName();
        if(defaultWorldName == null) throw new IllegalArgumentException("Could not get default world name");
        player.sendMessage("Default world name: " + defaultWorldName);

        // ItemStack as JSON
        player.sendMessage("Diamond pickaxe as json: " + ItemSerializer.toJson(new ItemStack(Material.DIAMOND_PICKAXE)));

        // Totem animation
        player.sendMessage("§dYou should see the totem animation now.");
        AnimationUtils.playTotemAnimation(player);

        // Tempt goal
        player.sendMessage("§bA villager should be following you now when you have an emerald in your hand.");
        player.getInventory().setItemInMainHand(new ItemStack(Material.EMERALD));
        Villager villager = player.getWorld().spawn(player.getLocation().add(5,0,5), Villager.class);
        villager.setCustomName("Emerald Seeker");
        EntityUtils.addPathfinderGoal(villager, Pathfinders.createTemptGoal(villager, Stream.of(Material.EMERALD)),0);


        player.sendMessage("§dA llama should be following you now all the time");
        Llama llama = player.getWorld().spawn(player.getLocation().add(-5, 0, -5), Llama.class);
        llama.setCustomName("Player Seeker");
        EntityUtils.addPathfinderGoal(llama, new CustomGoal(llama) {
            @Override
            public boolean canUse() {
                return true;
            }

            @Override
            public void tick() {
                Player closest = EntityUtils.getClosestPlayer(getBukkitEntity());
                if(closest != null) {
                    getNavigation().moveTo(closest.getLocation(), 1);
                }
            }
        }, 0);

        player.sendMessage("§aSeems to be working!");

    }

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
            return itemstack.clone();
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
