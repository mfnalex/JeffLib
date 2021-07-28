package de.jeff_media.jefflib;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Proudly stolen from EssentialsX
 */

/**
 * Deprecated. Use {@link de.jeff_media.jefflib.McVersion} instead
 * @deprecated
 */
@Deprecated
public final class VersionUtil {

    public static final BukkitVersion v1_14_R01 = BukkitVersion.fromString("1.14-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_8_8_R01 = BukkitVersion.fromString("1.8.8-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_9_R01 = BukkitVersion.fromString("1.9-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_9_4_R01 = BukkitVersion.fromString("1.9.4-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_10_2_R01 = BukkitVersion.fromString("1.10.2-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_11_R01 = BukkitVersion.fromString("1.11-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_11_2_R01 = BukkitVersion.fromString("1.11.2-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_12_0_R01 = BukkitVersion.fromString("1.12.0-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_12_2_R01 = BukkitVersion.fromString("1.12.2-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_13_0_R01 = BukkitVersion.fromString("1.13.0-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_13_2_R01 = BukkitVersion.fromString("1.13.2-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_14_4_R01 = BukkitVersion.fromString("1.14.4-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_15_R01 = BukkitVersion.fromString("1.15-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_15_2_R01 = BukkitVersion.fromString("1.15.2-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_16_1_R01 = BukkitVersion.fromString("1.16.1-R0.1-SNAPSHOT");
    public static final BukkitVersion v1_16_5_R01 = BukkitVersion.fromString("1.16.5-R0.1-SNAPSHOT");

    private static final Set<BukkitVersion> supportedVersions = ImmutableSet.of(v1_8_8_R01, v1_9_4_R01, v1_10_2_R01, v1_11_2_R01, v1_12_2_R01, v1_13_2_R01, v1_14_4_R01, v1_15_2_R01, v1_16_5_R01);

    private static final Map<String, SupportStatus> unsupportedServerClasses;
    private static BukkitVersion serverVersion = null;
    private static SupportStatus supportStatus = null;
    // Used to find the specific class that caused a given support status
    private static String supportStatusClass = null;

    static {
        final ImmutableMap.Builder<String, SupportStatus> builder = new ImmutableMap.Builder<>();

        // Yatopia - Extremely volatile patch set;
        //   * Messes with proxy-forwarded UUIDs
        //   * Frequent data corruptions
        builder.put("org.yatopiamc.yatopia.server.YatopiaConfig", SupportStatus.DANGEROUS_FORK);

        // KibblePatcher - Dangerous bytecode editor snakeoil whose only use is to break plugins
        builder.put("net.kibblelands.server.FastMath", SupportStatus.DANGEROUS_FORK);

        // Akarin - Dangerous patch history;
        //   * Potentially unsafe saving of nms.JsonList
        builder.put("io.akarin.server.Config", SupportStatus.DANGEROUS_FORK);

        // Forge - Doesn't support Bukkit
        builder.put("net.minecraftforge.common.MinecraftForge", SupportStatus.UNSTABLE);

        // Fabric - Doesn't support Bukkit
        builder.put("net.fabricmc.loader.launch.knot.KnotServer", SupportStatus.UNSTABLE);

        // Misc translation layers that do not add NMS will be caught by this
        builder.put("!net.minecraft.server." + ReflUtils.getNMSVersion() + ".MinecraftServer", SupportStatus.NMS_CLEANROOM);

        unsupportedServerClasses = builder.build();
    }

    private VersionUtil() {
    }

    public static BukkitVersion getServerBukkitVersion() {
        if (serverVersion == null) {
            serverVersion = BukkitVersion.fromString(Bukkit.getServer().getBukkitVersion());
        }
        return serverVersion;
    }

    public static SupportStatus getServerSupportStatus() {
        if (supportStatus == null) {
            for (Map.Entry<String, SupportStatus> entry : unsupportedServerClasses.entrySet()) {
                final boolean inverted = entry.getKey().contains("!");
                final String clazz = entry.getKey().replace("!", "");
                try {
                    Class.forName(clazz);
                    if (!inverted) {
                        supportStatusClass = entry.getKey();
                        return supportStatus = entry.getValue();
                    }
                } catch (final ClassNotFoundException ignored) {
                    if (inverted) {
                        supportStatusClass = entry.getKey();
                        return supportStatus = entry.getValue();
                    }
                }
            }

            if (!supportedVersions.contains(getServerBukkitVersion())) {
                return supportStatus = SupportStatus.OUTDATED;
            }

            return supportStatus = PaperLib.isPaper() ? SupportStatus.FULL : SupportStatus.LIMITED;
        }
        return supportStatus;
    }

    public static String getSupportStatusClass() {
        return supportStatusClass;
    }

    public static boolean isServerSupported() {
        return getServerSupportStatus().isSupported();
    }

    public enum SupportStatus {
        FULL(true),
        LIMITED(true),
        DANGEROUS_FORK(false),
        NMS_CLEANROOM(false),
        UNSTABLE(false),
        OUTDATED(false);

        private final boolean supported;

        SupportStatus(final boolean supported) {
            this.supported = supported;
        }

        public boolean isSupported() {
            return supported;
        }
    }

    public static final class BukkitVersion implements Comparable<BukkitVersion> {
        private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)\\.?([0-9]*)?(?:-pre(\\d))?(?:-?R?([\\d.]+))?(?:-SNAPSHOT)?");

        private final int major;
        private final int minor;
        private final int prerelease;
        private final int patch;
        private final double revision;

        private BukkitVersion(final int major, final int minor, final int patch, final double revision, final int prerelease) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
            this.revision = revision;
            this.prerelease = prerelease;
        }

        public static BukkitVersion fromString(final String string) {
            Preconditions.checkNotNull(string, "string cannot be null.");
            Matcher matcher = VERSION_PATTERN.matcher(string);
            if (!matcher.matches()) {
                if (!Bukkit.getName().equals("Essentials Fake Server")) {
                    throw new IllegalArgumentException(string + " is not in valid version format. e.g. 1.8.8-R0.1");
                }
                matcher = VERSION_PATTERN.matcher(v1_14_R01.toString());
                Preconditions.checkArgument(matcher.matches(), string + " is not in valid version format. e.g. 1.8.8-R0.1");
            }

            return from(matcher.group(1), matcher.group(2), matcher.group(3), matcher.groupCount() < 5 ? "" : matcher.group(5), matcher.group(4));
        }

        private static BukkitVersion from(final String major, final String minor, String patch, String revision, String prerelease) {
            if (patch == null || patch.isEmpty()) patch = "0";
            if (revision == null || revision.isEmpty()) revision = "0";
            if (prerelease == null || prerelease.isEmpty()) prerelease = "-1";
            return new BukkitVersion(Integer.parseInt(major),
                    Integer.parseInt(minor),
                    Integer.parseInt(patch),
                    Double.parseDouble(revision),
                    Integer.parseInt(prerelease));
        }

        public boolean isHigherThan(final BukkitVersion o) {
            return compareTo(o) > 0;
        }

        public boolean isHigherThanOrEqualTo(final BukkitVersion o) {
            return compareTo(o) >= 0;
        }

        public boolean isLowerThan(final BukkitVersion o) {
            return compareTo(o) < 0;
        }

        public boolean isLowerThanOrEqualTo(final BukkitVersion o) {
            return compareTo(o) <= 0;
        }

        public int getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }

        public int getPatch() {
            return patch;
        }

        public double getRevision() {
            return revision;
        }

        public int getPrerelease() {
            return prerelease;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final BukkitVersion that = (BukkitVersion) o;
            return major == that.major &&
                    minor == that.minor &&
                    patch == that.patch &&
                    revision == that.revision &&
                    prerelease == that.prerelease;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(major, minor, patch, revision, prerelease);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(major + "." + minor);
            if (patch != 0) {
                sb.append(".").append(patch);
            }
            if (prerelease != -1) {
                sb.append("-pre").append(prerelease);
            }
            return sb.append("-R").append(revision).toString();
        }

        @Override
        public int compareTo(final BukkitVersion o) {
            if (major < o.major) {
                return -1;
            } else if (major > o.major) {
                return 1;
            } else { // equal major
                if (minor < o.minor) {
                    return -1;
                } else if (minor > o.minor) {
                    return 1;
                } else { // equal minor
                    if (patch < o.patch) {
                        return -1;
                    } else if (patch > o.patch) {
                        return 1;
                    } else { // equal patch
                        if (prerelease < o.prerelease) {
                            return -1;
                        } else if (prerelease > o.prerelease) {
                            return 1;
                        } else { // equal prerelease
                            return Double.compare(revision, o.revision);
                        }
                    }
                }
            }
        }
    }
}
