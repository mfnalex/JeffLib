package com.jeff_media.jefflib.pluginhooks.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import lombok.Getter;

class WorldGuardStateFlag extends StateFlag {

    @Getter private com.sk89q.worldguard.protection.flags.StateFlag worldGuardStateFlag;

    WorldGuardStateFlag(String name, State defaultValue) {
        super(name, defaultValue);

        com.sk89q.worldguard.protection.flags.StateFlag stateFlag = new com.sk89q.worldguard.protection.flags.StateFlag(name, defaultValue.asBoolean());
        try {
            WorldGuard.getInstance().getFlagRegistry().register(stateFlag);
        } catch (FlagConflictException exception) {
            Flag<?> existing = WorldGuard.getInstance().getFlagRegistry().get(name);
            if(existing instanceof com.sk89q.worldguard.protection.flags.StateFlag) {
                stateFlag = (com.sk89q.worldguard.protection.flags.StateFlag) existing;
            } else {
                throw exception;
            }
        }
    }
}
