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

package com.jeff_media.jefflib;

import org.bukkit.entity.EntityType;

public class OldBukkitEnums {

    private static final EntityTypes ENTITY_TYPES = new EntityTypes();

    public static EntityTypes entityTypes() {
        return ENTITY_TYPES;
    }



    public static class EntityTypes {
        public final EntityType ENDER_CRYSTAL;
        public final EntityType PRIMED_TNT;

        private EntityTypes() {
            ENDER_CRYSTAL = EnumUtils.getIfPresent(EntityType.class, "ENDER_CRYSTAL", "END_CRYSTAL").orElse(null);
            PRIMED_TNT = EnumUtils.getIfPresent(EntityType.class, "PRIMED_TNT", "TNT").orElse(null);
        }
    }
}
