package com.jefflib.jefflibtestplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("swing")
public class SwingCommand extends BaseCommand {

    @Default
    public void swing(Player player) {
        player.swingMainHand();
    }
}
