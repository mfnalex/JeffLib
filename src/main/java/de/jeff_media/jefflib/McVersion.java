package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

public final class McVersion {

    private static Integer major, minor, patch;

    public static int getMajor() {
        if (major != null) return major;
        String majorString = Bukkit.getBukkitVersion().split("\\.")[0];
        System.out.println("major: " + majorString);
        return major;
    }

    public static int getMinor() {
        if (minor != null) return minor;
        String minorString = Bukkit.getBukkitVersion().split("\\.")[1];
        if(minorString.contains("-")) {
            minorString = minorString.split("-")[0];
            patch = 0;
        }
        minor = Integer.parseInt(minorString);
        return minor;
    }

    public static int getPatch() {
        if (patch != null) return patch;
        String patchString = Bukkit.getBukkitVersion().split("\\.")[2];
        patch = Integer.parseInt(patchString);
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
