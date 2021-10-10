package de.jeff_media.jefflib;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class UselessUtils {

    public static int getItemStackMaxAmount(final ItemStack item) {
        return item.getMaxStackSize();
    }

    public static void getItemStackMaxAmountButDontReturnIt(final ItemStack item) {
        item.getMaxStackSize();
    }

    @SneakyThrows
    public static Cloneable getClone(final Cloneable cloneable){
        return (Cloneable) cloneable.getClass().getDeclaredMethod("clone").invoke(cloneable);
    }

    public static void printCazcez() {
        System.out.println("Cazcez");
    }
}
