package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

public final class McVersion {

    private static Integer major, minor, patch;

    public static int getMajor() {
        if (major != null) return major;
        major = getVersionField(0);
        return major;
    }

    public static int getMinor() {
        if (minor != null) return minor;
        minor = getVersionField(1);
        return minor;
    }

    public static int getPatch() {
        if (patch != null) return patch;
        patch = getVersionField(2);
        return patch;
    }

    private static int getVersionField(int index) {
        return Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[index]);
    }

    public boolean isAtLeast(int major, int minor, int patch) {
        if (getMajor() > major) return true;
        if (getMinor() > minor) return true;
        if (getPatch() > patch) return true;
        return getMajor() == major && getMinor() == minor && getPatch() == patch;
    }

}
