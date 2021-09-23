package de.jeff_media.jefflib;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class UselessUtils {

    public static int getItemStackMaxAmount(ItemStack item) {
        return item.getMaxStackSize();
    }

    public static void getItemStackMaxAmountButDontReturnIt(ItemStack item) {
        item.getMaxStackSize();
    }

    @SneakyThrows
    public static Cloneable getClone(Cloneable cloneable){
        return (Cloneable) cloneable.getClass().getDeclaredMethod("clone").invoke(cloneable);
    }

    public static void printCazcez() {
        System.out.println("Cazcez");
    }
}
