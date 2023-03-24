/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jefflib.jefflibtestplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.jefflib.jefflibtestplugin.JeffLibTestPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandAlias("jefflibtest")
public class JeffLibTestCommand extends BaseCommand {

    private final JeffLibTestPlugin plugin;

    public JeffLibTestCommand(JeffLibTestPlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("start")
    @Default
    public void start(CommandSender sender) {
        Player player = sender instanceof Player ? (Player) sender : null;
        plugin.createTestRunner(player);
        next("force");
    }

    @Subcommand("repeat")
    public void repeat(String confirmation) {
        if(!isConfirmationCorrect(confirmation)) return;
        plugin.getTestRunner().repeat();
    }

    @Subcommand("confirm")
    public void next(String confirmation) {
        if(plugin.getTestRunner()==null) {
            //player.sendMessage("NMSTest runner not initialized");
            return;
        }
        if(!isConfirmationCorrect(confirmation)) return;
        plugin.getTestRunner().setWaitingForTestResult(false);
    }

    private boolean isConfirmationCorrect(String confirmation) {
        if(confirmation == null) {
            confirmation = "";
        }
        if(confirmation.equals("force")) {
            return true;
        }
        if(plugin.getTestRunner() == null) {
            return false;
        }
        return Objects.equals(plugin.getTestRunner().getSession(), confirmation);
    }


}
