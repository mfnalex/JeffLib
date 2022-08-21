/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib;

import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.PathfinderGoals;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Pig;
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

    /**
     * Prints a Map's content to console. Example:
     * <pre>
     * firstKey -> firstValue
     * secondKey -> secondValue
     * </pre>
     */
    public static void print(final Map<?, ?> map) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static void print(final ItemStack[] items) {
        System.out.println("ItemStack[" + items.length + "]");
        for (final ItemStack item : items) {
            if (item == null) continue;
            System.out.println(" - " + item);
        }
    }

    public static ItemStack getShell(ShellType type) {
        return type.getItemstack().clone();
    }

    public static void print(final Collection<?> collection) {
        collection.forEach(System.out::println);
    }

    public enum ShellType {
        NAUTILUS_SHELL(Material.NAUTILUS_SHELL), TURTLE_SHELL(Material.TURTLE_HELMET), SHULKER_SHELL(Material.SHULKER_SHELL);

        @Getter
        private final Material material;
        private final ItemStack itemstack;

        ShellType(Material material) {
            this.material = material;
            this.itemstack = new ItemStack(material);
        }

        public ItemStack getItemstack() {
            return itemstack.clone();
        }
    }

    public static class NMSTest {

        private final Player player;

        public NMSTest(final Player player) {
            this.player = player;
        }

        public void testNms() {
            try {
                JeffLib.enableNMS();

                // NMS version
                testNMSVersion();

                // Default world name
                testDefaultWorldName();

                // ItemStack as JSON
                testItemStackToJson();

                // Totem animation
                testTotemAnimation();

                // Tempt goal
                testTemptGoal();

                // Custom goal (follow Player)
                testCustomGoalFollowPlayer();

                // Avoid entity goal
                testAvoidEntityGoal();

                // Current biome name
                if (McVersion.current().isAtLeast(1, 16, 2)) {
                    testBiomeName();
                }

                player.sendMessage("§aSeems to be working!");
            } catch (Throwable t) {
                player.sendMessage("Error!");
                player.sendMessage(t.getMessage());
                t.printStackTrace();
            }

        }

        private void testNMSVersion() {
            McVersion version = McVersion.current();
            String nmsVersion = version.getNmsVersion();
            if (nmsVersion == null) throw new IllegalArgumentException("Could not get NMS version");
            player.sendMessage("NMS version: " + nmsVersion);
        }

        private void testDefaultWorldName() {
            String defaultWorldName = WorldUtils.getDefaultWorldName();
            if (defaultWorldName == null) throw new IllegalArgumentException("Could not get default world name");
            player.sendMessage("Default world name: " + defaultWorldName);
        }

        private void testItemStackToJson() {
            player.sendMessage("Diamond pickaxe as json: " + ItemSerializer.toJson(new ItemStack(Material.DIAMOND_PICKAXE)));
        }

        private void testTotemAnimation() {
            player.sendMessage("§dYou should see the totem animation now.");
            AnimationUtils.playTotemAnimation(player);
        }

        private void testTemptGoal() {
            player.sendMessage("§bA villager should be following you now when you have an emerald in your hand.");
            player.getInventory().setItemInMainHand(new ItemStack(Material.EMERALD));
            Villager villager = player.getWorld().spawn(player.getLocation().add(5, 0, 5), Villager.class);
            villager.setCustomName("Emerald Seeker");
            villager.setCustomNameVisible(true);
            EntityUtils.getGoalSelector(villager).addGoal(PathfinderGoals.temptGoal(villager, Stream.of(Material.EMERALD), 1D, false), 0);
        }

        private void testCustomGoalFollowPlayer() {
            player.sendMessage("§dA llama should be following you now all the time");
            Llama llama = player.getWorld().spawn(player.getLocation().add(-5, 0, -5), Llama.class);
            llama.setCustomName("Player Seeker");
            llama.setCustomNameVisible(true);
            EntityUtils.getGoalSelector(llama).addGoal(new CustomGoal(llama) {
                @Override
                public boolean canUse() {
                    return true;
                }

                @Override
                public void tick() {
                    Player closest = EntityUtils.getClosestPlayer(getBukkitEntity());
                    if (closest != null) {
                        getNavigation().moveTo(closest.getLocation(), 2.5);
                    }
                }
            }, 0);
        }

        private void testAvoidEntityGoal() {
            player.sendMessage("§eA pig that's afraid by emeralds should be nearby you now");
            Pig pig = player.getWorld().spawn(player.getLocation(), Pig.class);
            pig.setCustomName("Emerald hater");
            pig.setCustomNameVisible(true);
            EntityUtils.getGoalSelector(pig).addGoal(PathfinderGoals.avoidEntity(pig, ent -> {
                if (ent instanceof Player) {
                    ItemStack hand = ((Player) ent).getInventory().getItemInMainHand();
                    if (hand == null) return false;
                    return hand.getType() == Material.EMERALD;
                }
                return false;
            }, 30, 1, 2), 0);
        }

        private void testBiomeName() {
            NamespacedKey key = BiomeUtils.getBiomeNamespacedKey(player.getLocation());
            player.sendMessage("Biome: " + key.toString());
        }
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
