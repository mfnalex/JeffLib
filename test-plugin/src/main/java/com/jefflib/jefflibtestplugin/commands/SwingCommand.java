package com.jefflib.jefflibtestplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.jeff_media.jefflib.AnimationUtils;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;

@CommandAlias("swing")
public class SwingCommand extends BaseCommand {

    @Subcommand("bukkit")
    public void swing(Player player) {
        player.playEffect(EntityEffect.TOTEM_RESURRECT);
    }

    @Subcommand("nms")
    public void swingNMS(Player player) {
        AnimationUtils.playTotemAnimation(player);

    }
}
