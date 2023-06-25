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

package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.ai.goal.PathfinderGoals;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

public class MoveToBlockGoal implements NMSTest {

    private Location spawnLoc;
    private Block targetBlock;
    private BlockData originalBlockData;
    private Block belowTargetBlock;
    private Pig pig;
    private Set<Material> materials = new HashSet<Material>() {
        {
            add(Material.GOLD_BLOCK);
        }
    };

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        spawnLoc = runner.getSpawn().add(3, 0, 7);
        targetBlock = runner.getSpawn().add(-3, 0, 7).getBlock();
        belowTargetBlock = targetBlock.getRelative(0, -1, 0);
        originalBlockData = belowTargetBlock.getBlockData();

        belowTargetBlock.setType(Material.GOLD_BLOCK);

        pig = player.getWorld().spawn(spawnLoc, Pig.class);
        EntityUtils.getGoalSelector(pig).removeAllGoals();
        EntityUtils.getGoalSelector(pig).addGoal(PathfinderGoals.moveToBlock(pig, materials, 1, 10, 2), 1);

    }

    @Override
    public void cleanup() {
        belowTargetBlock.setBlockData(originalBlockData);
    }

    @Override
    public boolean isRunnableFromConsole() {
        return false;
    }

    @Override
    public boolean isDone() {
        double distance = pig.getLocation().distance(targetBlock.getLocation());
        System.out.println(distance);
        return distance < 2;
    }
}
