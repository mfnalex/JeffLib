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

package com.jeff_media.jefflib.pluginhooks.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import lombok.Getter;

class WorldGuardStateFlag extends StateFlag {

    @Getter
    private com.sk89q.worldguard.protection.flags.StateFlag worldGuardStateFlag;

    WorldGuardStateFlag(String name, State defaultValue) {
        super(name, defaultValue);

        com.sk89q.worldguard.protection.flags.StateFlag stateFlag = new com.sk89q.worldguard.protection.flags.StateFlag(name, defaultValue.asBoolean());
        try {
            WorldGuard.getInstance().getFlagRegistry().register(stateFlag);
        } catch (FlagConflictException exception) {
            Flag<?> existing = WorldGuard.getInstance().getFlagRegistry().get(name);
            if (existing instanceof com.sk89q.worldguard.protection.flags.StateFlag) {
                stateFlag = (com.sk89q.worldguard.protection.flags.StateFlag) existing;
            } else {
                throw exception;
            }
        }
    }
}
