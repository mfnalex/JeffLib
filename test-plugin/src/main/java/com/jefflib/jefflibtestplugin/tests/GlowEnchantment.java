package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.EnchantmentUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class GlowEnchantment implements NMSTest {

    private Player player;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        ItemStack item = new ItemStack(Material.DIAMOND);
        EnchantmentUtils.addGlowEffect(item);
        if(player != null) {
            this.player = player;
            player.getInventory().setItemInMainHand(item);
        }
    }

    @Override
    public boolean isRunnableFromConsole() {
        return true;
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Does the diamond glow?";
    }

    @Override
    public void cleanup() {
        if(player != null) {
            player.getInventory().setItemInMainHand(null);
        }
    }
}
