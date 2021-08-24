package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

/**
 * Represents a Minecraft version, e.g. "1.16.5"
 */
public final class McVersion {

    private static Integer major, minor, patch;

    /**
     * Returns the Major version of the MC version, e.g. 1 for 1.16.5
     *
     * @return Major version of the MC version
     */
    public static int getMajor() {
        if (major != null) return major;
        String string = Bukkit.getBukkitVersion().split("\\.")[0];
        major = Integer.parseInt(string);
        return major;
    }

    /**
     * Returns the Minor version of the MC version, e.g. 16 for 1.16.5
     *
     * @return Minor version of the MC version
     */
    public static int getMinor() {
        if (minor != null) return minor;
        String string = Bukkit.getBukkitVersion().split("\\.")[1].split("-")[0];
        minor = Integer.parseInt(string);
        return minor;
    }

    /**
     * Returns the Patch version of the MC version, e.g. 5 for 1.16.5
     *
     * @return Patch version of the MC version
     */
    public static int getPatch() {
        if (patch != null) return patch;
        if (Bukkit.getBukkitVersion().chars().filter(ch -> ch == '.').count() == 2) {
            patch = 0;
        } else {
            String string = Bukkit.getBukkitVersion().split("\\.")[2].split("-")[0];
            patch = Integer.valueOf(string);
        }
        return patch;
    }

    /**
     * Checks whether the currently running MC version is at least the given version. For example, isAtLeast(1.16.4) will return true for all versions including and above 1.16.4
     *
     * @param major Major version
     * @param minor Minor version
     * @param patch Patch version
     * @return true when the currently running MC version is at least the given version, otherwise false
     */
    public static boolean isAtLeast(int major, int minor, int patch) {
        if (major > getMajor()) {
            return false;
        }
        if (getMajor() > major) {
            return true;
        }
        if (minor > getMinor()) {
            return false;
        }
        if (getMinor() > minor) {
            return true;
        }
        return getPatch() >= patch;

    }

}
