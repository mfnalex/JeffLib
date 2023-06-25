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

import java.util.Objects;
import lombok.Getter;

public class StateFlag {

    @Getter
    private final String name;
    @Getter
    private final State defaultValue;

    StateFlag(String name, State defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateFlag stateFlag = (StateFlag) o;
        return name.equals(stateFlag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public enum State {
        ALLOW,
        DENY;

        public static State fromBoolean(boolean aBoolean) {
            return aBoolean ? ALLOW : DENY;
        }

        public boolean asBoolean() {
            return this == ALLOW;
        }
    }

}
