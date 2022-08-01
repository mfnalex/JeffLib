package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

/**
 * Word related methods, like capitalization
 */
@UtilityClass
public final class WordUtils {

    /**
     * Turns Material names into a nicer name. E.g. DIAMOND_PICKAXE will return "Diamond Pickaxe"
     *
     * @param mat The Material
     * @return Human readable name
     * @deprecated Use {@link MaterialUtils#getNiceMaterialName} instead
     */
    @Deprecated
    public static String getNiceMaterialName(final Material mat) {
        final StringBuilder builder = new StringBuilder();
        final Iterator<String> iterator = Arrays.stream(mat.name().split("_")).iterator();
        while (iterator.hasNext()) {
            builder.append(upperCaseFirstLetterOnly(iterator.next()));
            if (iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Turns the first letter of a String to uppercase, while making the rest lowercase
     *
     * @param word String to change
     */
    public static String upperCaseFirstLetterOnly(final String word) {
        return upperCaseFirstLetter(word.toLowerCase(Locale.ROOT));
    }

    /**
     * Turns the first letter of a String to uppercase
     *
     * @param word String to change
     */
    public static String upperCaseFirstLetter(final String word) {
        if (word.length() < 1) return word;
        if (word.length() == 1) return word.toUpperCase(Locale.ROOT);
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
    }

    /**
     * Converts a NamespacedKey into a human readable name, ignoring the namespace. For example, "minecraft:warm_ocean" will return "Warm Ocean"
     *
     * @param key NamespacedKey
     * @return Human readable key name
     */
    public static String getNiceName(@Nonnull final NamespacedKey key) {
        return getNiceName(key.getKey());
    }

    public static String getNiceName(@Nonnull final String string) {
        final String[] split = string.split("_");
        final Iterator<String> iterator = Arrays.stream(split).iterator();
        final StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(upperCaseFirstLetterOnly(iterator.next().toLowerCase(Locale.ROOT)));
            if (iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }
    
    /**
     * Builds a String from the given args and starting point
     *
     * @param String[] args String Array
     * @param int start Starting index to append strings together with
     * @return String The final String
     */
    public static String buildString(String[] args, int start) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = start; i < args.length; i++) {
            stringBuilder.append(args[i]).append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.toString().length()-1);
    }
    
    /**
     * Converts a Unix Timestamp to a String
     *
     * @param long unix A Unix timestamp
     * @return String The resulted String
     */
    public static String dateToString(long unix) {
        Date date = new Date(unix);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        format.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        return format.format(date);
    }
    
    /**
     * Converts seconds to Days, Hours, Minutes, Seconds
     *
     * @param long seconds The given amount of seconds
     * @return String The resulted String
     */
    public static String secondsToDDHHMMSS(long seconds) {
        return String.format("%02dd %02dh %02dm %02ds", seconds / 86400, (seconds / 3600 % 24), (seconds / 60) % 60, seconds % 60);
    }

    /**
     * Converts seconds to Hours, Minutes, Seconds
     *
     * @param long seconds The given amount of seconds
     * @return String The resulted String
     */
    public static String secondsToHHMMSS(long seconds) {
        return String.format("%02dh %02dm %02ds", seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }
}
