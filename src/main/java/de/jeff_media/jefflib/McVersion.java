package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

public final class McVersion {

    private static Integer major, minor, patch;

    public boolean isAtLeast(int major, int minor, int patch) {
        if(getMajor() > major) return true;
        if(getMinor() > minor) return true;
        if(getPatch() > patch) return true;
        if(getMajor() == major && getMinor() == minor && patch == patch) return true;
        return false;
    }

    public static int getMajor() {
        if(major != null) return major;
        major = getVersionField(0);
        return major;
    }

    public static int getMinor() {
        if(minor != null) return minor;
        minor = getVersionField(1);
        return minor;
    }

    public static int getPatch() {
        if(patch != null) return patch;
        patch = getVersionField(2);
        return patch;
    }

    private static int getVersionField(int index) {
        return Integer.valueOf(Bukkit.getBukkitVersion().split("\\.")[index]);
    }

}
