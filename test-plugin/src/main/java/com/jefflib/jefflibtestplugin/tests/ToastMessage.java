package com.jefflib.jefflibtestplugin.tests;

import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class ToastMessage implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        com.jeff_media.jefflib.data.ToastMessage toastAdvancement = new com.jeff_media.jefflib.data.ToastMessage("Hello advancement 1", Material.COAL_ORE, com.jeff_media.jefflib.data.ToastMessage.FrameType.ADVANCEMENT, false);
        com.jeff_media.jefflib.data.ToastMessage toastChallenge = new com.jeff_media.jefflib.data.ToastMessage("Hello challenge 2", Material.IRON_INGOT, com.jeff_media.jefflib.data.ToastMessage.FrameType.CHALLENGE, false);
        com.jeff_media.jefflib.data.ToastMessage toastGoal = new com.jeff_media.jefflib.data.ToastMessage("Hello goal 3", Material.DIAMOND, com.jeff_media.jefflib.data.ToastMessage.FrameType.GOAL, false);

        if(player != null) {
            for (com.jeff_media.jefflib.data.ToastMessage toast : new com.jeff_media.jefflib.data.ToastMessage[]{toastAdvancement, toastChallenge, toastGoal}) {
                toast.send(player);
            }
        }
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Did you see three toast messages?";
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }
}
