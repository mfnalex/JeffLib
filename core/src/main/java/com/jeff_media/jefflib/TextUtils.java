/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.HexColor;
import com.jeff_media.jefflib.internal.cherokee.StringUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Methods related to color translation, placeholder and emoji application and more
 */
@UtilityClass
public class TextUtils {

    private static final int MIN_BANNER_WIDTH = 30;
    private static final char BANNER_CHAR = '#';
    private static final String EMPTY = "";
    private static final String REGEX_HEX = "[\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F]";

    private static final String REGEX_HEX_GRADIENT = "<#([\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F])>(.*?)<#/([\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F])>";
    private static final Pattern PATTERN_HEX_GRADIENT = Pattern.compile(REGEX_HEX_GRADIENT);

    private static final String REGEX_AMPERSAND_HASH = "&#(" + REGEX_HEX + ")";
    private static final Pattern PATTERN_AMPERSAND_HASH = Pattern.compile(REGEX_AMPERSAND_HASH);
    private static final String REGEX_XML_LIKE_HASH = "<#(" + REGEX_HEX + ")>";
    private static final Pattern PATTERN_XML_LIKE_HASH = Pattern.compile(REGEX_XML_LIKE_HASH);
    private static AtomicReference<Plugin> itemsAdderPlugin;
    private static AtomicReference<Plugin> placeholderApiPlugin;

    /**
     * Prints a banner / headline to console
     *
     * @param text text to be printed
     */
    public static void banner(final CharSequence text) {

        final int bannerWidth = Math.max(text.length() + 4, MIN_BANNER_WIDTH);
        StringUtils.leftPad(EMPTY, bannerWidth, BANNER_CHAR);
        JeffLib.getPlugin().getLogger().info(StringUtils.center(" " + text + " ", bannerWidth, BANNER_CHAR));
    }

    /**
     * Replaces Emojis, PlacederholderAPI placeholders and colors (see {@link #color(String)})
     *
     * @param text Text to translate
     * @return Translated text
     */
    public static String format(final String text) {
        return format(text, null);
    }

    /**
     * Replaces Emojis, PlacedeholderAPI placeholders and colors (see {@link #color(String)})
     *
     * @param text   Text to translate
     * @param player Player to apply placeholders for, or null
     * @return Translated text
     */
    public static String format(String text, @Nullable final OfflinePlayer player) {
        text = replaceEmojis(text);
        text = replacePlaceholders(text, player);
        text = color(text);
        return text;
    }

    /**
     * Replaces ItemsAdder emojis inside the given text when ItemsAdder is installed
     *
     * @param text Text to apply emojis to
     * @return Translated text
     */
    @SuppressWarnings("NonThreadSafeLazyInitialization")
    public static String replaceEmojis(String text) {
        if (itemsAdderPlugin == null) {
            itemsAdderPlugin = new AtomicReference<>(Bukkit.getPluginManager().getPlugin("ItemsAdder"));
        }
        if (itemsAdderPlugin.get() != null) {
            text = dev.lone.itemsadder.api.FontImages.FontImageWrapper.replaceFontImages(text);
        }
        return text;
    }

    /**
     * Replaces PlacerholderAPI placeholders inside the given text, when PlaceholderAPI is installed
     *
     * @param text   Text to translate
     * @param player Player to translate placeholders for, or null
     * @return Translated text
     */
    @SuppressWarnings("NonThreadSafeLazyInitialization")
    public static String replacePlaceholders(String text, @Nullable final OfflinePlayer player) {
        //System.out.println("Replacing placeholders");
        if (placeholderApiPlugin == null) {
            placeholderApiPlugin = new AtomicReference<>(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"));
        }
        if (placeholderApiPlugin.get() != null) {
            text = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    /**
     * Replaces color codes using &amp;. Also supports hex colors using <pre>&amp;x&amp;r&amp;r&amp;g&amp;g&amp;b&amp;b</pre>, <pre>&amp;#rrggbb</pre> and <pre>&lt;#rrggbb></pre>,
     * and gradients using <pre>&lt;#rrggbb> &lt;#/rrggbb></pre>
     *
     * @param text Text to translate
     * @return Translated text
     */
    public static String color(String text) {
        //text = replaceRegexWithGroup(text, PATTERN_AMPERSAND_HASH, 1, TextUtils::addAmpersandsToHex);
        text = text.replace("&&", "{ampersand}");
        text = replaceGradients(text);
        text = replaceRegexWithGroup(text, PATTERN_XML_LIKE_HASH, 1, TextUtils::addAmpersandsToHex);
        text = replaceRegexWithGroup(text, PATTERN_AMPERSAND_HASH, 1, TextUtils::addAmpersandsToHex);
        text = ChatColor.translateAlternateColorCodes('&', text);
        text = text.replace("{ampersand}", "&");
        return text;
    }

    private static String replaceGradients(String text) {

        text = text.replaceAll("<#/([\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F][\\da-fA-F])>", "<#/$1><#$1>");

        final Matcher matcher = PATTERN_HEX_GRADIENT.matcher(text);
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            final HexColor startColor = new HexColor(matcher.group(1));
            final HexColor endColor = new HexColor(matcher.group(3));
            final String partText = matcher.group(2);
            matcher.appendReplacement(sb, HexColor.applyGradient(partText, startColor, endColor));
        }
        matcher.appendTail(sb);
        String result = sb.toString();
        while (result.matches(".*&x&[\\da-zA-Z]&[\\da-zA-Z]&[\\da-zA-Z]&[\\da-zA-Z]&[\\da-zA-Z]&[\\da-zA-Z]$")) {
            result = result.substring(0, result.length() - 14);
        }
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    private static String replaceRegexWithGroup(final CharSequence text, final Pattern pattern, final int group, final Function<String, String> function) {
        final Matcher matcher = pattern.matcher(text);
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, function.apply(matcher.group(group)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String addAmpersandsToHex(final String hex) {
        if (hex.length() != 6) {
            throw new IllegalArgumentException("Hex-Codes must always have 6 letters.");
        }
        final char[] chars = hex.toCharArray();
        final StringBuilder sb = new StringBuilder("&x");
        for (final char aChar : chars) {
            sb.append("&").append(aChar);
        }
        return sb.toString();
    }

    /**
     * Replaces Emojis, PlaceholderAPI placeholders and colors ({see {@link #color(String)})
     *
     * @param text   Text to translate
     * @param player Player to apply placeholders for, or null
     * @return Translated text
     */
    public static List<String> format(List<String> text, @Nullable final OfflinePlayer player) {
        text = new ArrayList<>(text);
        for (int i = 0; i < text.size(); i++) {
            text.set(i, format(text.get(i), player));
        }
        return text;
    }

    /**
     * Replaces placeholders in a list of Strings.
     *
     * @see #replaceInString(String, Map)
     */
    public List<String> replaceInString(final List<String> strings, final Map<String, String> placeholders) {
        strings.replaceAll(string -> replaceInString(string, placeholders));
        return strings;
    }

    /**
     * Replaces placeholders in a String. Example:
     * <pre>
     * Map&lt;String,String> placeholders = new HashMap&lt;>();
     * placeholders.put("{player}",player.getName());
     * placeholders.put("{killer}",killer.getName());
     * String result = replaceInString("{player} was killed by {killer}.",placeholders);
     * </pre>
     */
    public String replaceInString(String string, final Map<String, String> placeholders) {
        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            string = string.replace(entry.getKey(), entry.getValue());
        }
        return string;
    }

    /**
     * Replaces placeholders in a list of Strings.
     *
     * @see #replaceInString(String, String...)
     */
    public List<String> replaceInString(final List<String> strings, final String... placeholders) {
        strings.replaceAll(string -> replaceInString(string, placeholders));
        return strings;
    }

    /**
     * Replaces placeholders in a String. Example:
     * <pre>
     * String result = replaceInString("{name} is {age} years old.",
     *      "{name}", "mfnalex",
     *      "{age}","27"
     * );
     * </pre>
     */
    public String replaceInString(String string, final String... placeholders) {
        if (placeholders.length % 2 != 0) {
            throw new IllegalArgumentException("placeholders must have an even length");
        }
        for (int i = 0; i < placeholders.length; i += 2) {
            if (placeholders[i] == null || placeholders[i + 1] == null) continue;
            string = string.replace(placeholders[i], placeholders[i + 1]);
        }
        return string;
    }

}
