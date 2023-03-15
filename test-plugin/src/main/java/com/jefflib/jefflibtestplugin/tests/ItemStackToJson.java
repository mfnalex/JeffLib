package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.ItemStackSerializer;
import com.jeff_media.jefflib.ItemStackUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestException;
import com.jefflib.jefflibtestplugin.TestRunner;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemStackToJson implements NMSTest {

    private final ItemStack item;

    {
        item = new ItemStack(Material.WOODEN_SWORD);
        item.setAmount(2);
        ItemStackUtils.setDisplayName(item, ChatColor.RED + "JeffLib NMSTest Item");
        ItemStackUtils.makeUnstackable(item);
    }

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        String json = ItemStackSerializer.toJson(item);
        runner.print(json);
        ItemStack cloned = ItemStackSerializer.fromJson(json);
        if(!item.equals(cloned) || !item.toString().equals(cloned.toString())) {
            throw new TestException("Itemstacks are not equal: " + item + " vs " + cloned);
        }
    }

    @Override
    public boolean isRunnableFromConsole() {
        return true;
    }

    @Override
    public boolean hasConfirmation() {
        return false;
    }
}
