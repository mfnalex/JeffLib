package com.jeff_media.jefflib;

import org.bukkit.Bukkit;
import javax.annotation.Nonnull;

import java.util.Objects;

/**
 * Provides version comparing methods
 */

public class McVersion implements Comparable<McVersion> {

    private static final McVersion CURRENT_VERSION;

    static {
        final int currentMajor = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[0]);
        final int currentMinor = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1].split("-")[0]);
        final int currentPatch = Bukkit.getBukkitVersion().chars().filter(ch -> ch == '.').count() == 2 ? 0 : Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[2].split("-")[0]);

        CURRENT_VERSION = new McVersion(currentMajor, currentMinor, currentPatch);
    }

    private final int major, minor, patch;

    public McVersion(final int major, final int minor) {
        this(major, minor, 0);
    }

    public McVersion(final int major, final int minor, final int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Gets the currently running McVersion
     */
    public static McVersion current() {
        return CURRENT_VERSION;
    }

    /**
     * Gets the "major" part of this McVersion. For 1.16.5, this would be 1
     */
    public int getMajor() {
        return major;
    }

    /**
     * Gets the "minor" part of this McVersion. For 1.16.5, this would be 16
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Gets the "patch" part of this McVersion. For 1.16.5, this would be 5.
     */
    public int getPatch() {
        return patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final McVersion mcVersion = (McVersion) o;
        return major == mcVersion.major && minor == mcVersion.minor && patch == mcVersion.patch;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        if (patch == 0) {
            return major + "." + minor;
        } else {
            return major + "." + minor + "." + patch;
        }
    }

    public boolean isAtLeast(final int major, final int minor, final int patch) {
        return this.isAtLeast(new McVersion(major, minor, patch));
    }

    public boolean isAtLeast(final McVersion other) {
        return this.compareTo(other) >= 0;
    }

    @Override
    public int compareTo(@Nonnull final McVersion other) {
        if (this.major > other.major) return 3;
        if (other.major > this.major) return -3;
        if (this.minor > other.minor) return 2;
        if (other.minor > this.minor) return -2;
        return Integer.compare(this.patch, other.patch);
    }

    public boolean isAtLeast(final int major, final int minor) {
        return this.isAtLeast(new McVersion(major, minor));
    }
}
