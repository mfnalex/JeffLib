package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

public final class McVersion {

    private static Integer major, minor, patch;

    public static int getMajor() {
        if (major != null) return major;
        major = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[0]);
        return major;
    }

    public static int getMinor() {
        if (minor != null) return minor;
        minor = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);
        return minor;
    }

    public static int getPatch() {
        if (patch != null) return patch;
        patch = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[2].split("-")[0]);
        return patch;
    }

    public static boolean isAtLeast(int major, int minor, int patch) {
        if(major > getMajor()) {
            return false;
        }
        if(getMajor() > major) {
            return true;
        }
        if(minor > getMinor()) {
            return false;
        }
        if(getMinor() > minor) {
            return true;
        }
        if( getPatch() >= patch) {
            return true;
        }
        return false;

    }

}
