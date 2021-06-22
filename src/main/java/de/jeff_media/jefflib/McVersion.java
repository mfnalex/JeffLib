package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

/**
 * Used to check the MC version
 */
public final class McVersion {

    private static Integer major, minor, patch;

    /**
     * Returns the Major version of the MC version, e.g. 1 for 1.16.5
     * @return Major version of the MC version
     */
    public static int getMajor() {
        if (major != null) return major;
        String majorString = Bukkit.getBukkitVersion().split("\\.")[0];
        System.out.println("major: " + majorString);
        return major;
    }

    /**
     * Returns the Minor version of the MC version, e.g. 16 for 1.16.5
     * @return Minor version of the MC version
     */
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

    /**
     * Returns the Patch version of the MC version, e.g. 5 for 1.16.5
     * @return Patch version of the MC version
     */
    public static int getPatch() {
        if (patch != null) return patch;
        String patchString = Bukkit.getBukkitVersion().split("\\.")[2];
        patch = Integer.parseInt(patchString);
        return patch;
    }

    /**
     * Checks whether the currently running MC version is at least the given version
     * @param major Major version
     * @param minor Minor version
     * @param patch Patch version
     * @return true when the currently running MC version is at least the given version, otherwise false
     */
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
