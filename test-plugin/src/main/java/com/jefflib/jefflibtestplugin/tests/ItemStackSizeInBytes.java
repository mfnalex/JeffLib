package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.ItemStackUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class ItemStackSizeInBytes implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = Objects.requireNonNull((BookMeta) item.getItemMeta());
        meta.addPage("Hello world");
        meta.addPage("Second page");
        item.setItemMeta(meta);
        runner.print("ItemStack size: " + ItemStackUtils.getSizeInBytes(item) + " bytes");
    }
}
