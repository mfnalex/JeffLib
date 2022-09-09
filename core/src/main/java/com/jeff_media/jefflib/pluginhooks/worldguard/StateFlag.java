package com.jeff_media.jefflib.pluginhooks.worldguard;

import lombok.Getter;

import java.util.Objects;

public class StateFlag {

    @Getter private final String name;
    @Getter private final State defaultValue;

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

    StateFlag(String name, State defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
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
