/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.ItemStackUtils;
import com.jeff_media.jefflib.NumberUtils;
import com.jeff_media.jefflib.RandomUtils;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Data
public class VariableItemStack {

    @Nonnull
    private final ItemStack itemStack;
    private final int minAmount;
    private final int maxAmount;
    private final double chance;

    public static VariableItemStack fromConfigurationSection(final ConfigurationSection section) {
        final ItemStack itemStack = ItemStackUtils.fromConfigurationSection(section);
        Integer minAmount = null;
        Integer maxAmount = null;
        if (section.isSet("amount") && section.getString("amount").contains("-")) {
            minAmount = NumberUtils.parseInteger(section.getString("amount").split("-")[0]);
            maxAmount = NumberUtils.parseInteger(section.getString("amount").split("-")[1]);
        }
        if (minAmount == null || maxAmount == null) {
            minAmount = itemStack.getAmount();
            maxAmount = itemStack.getAmount();
        }
        if (minAmount > maxAmount) {
            minAmount = maxAmount;
        }
        double chance = 100;
        if (section.isSet("chance")) {
            if (section.isDouble("chance")) {
                chance = section.getDouble("chance");
            } else {
                chance = Double.parseDouble(section.getString("chance").replace("%", ""));
            }
        }
        return new VariableItemStack(itemStack, minAmount, maxAmount, chance);
    }

    public ItemStack getItemStack() {
        final ItemStack clone = itemStack.clone();
        clone.setAmount(RandomUtils.getInt(minAmount, maxAmount));
        return clone;
    }

}
