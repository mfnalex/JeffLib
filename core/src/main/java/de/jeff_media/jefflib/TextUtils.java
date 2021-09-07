package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.HexColor;
import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
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
    private static final String REGEX_HEX = "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]";

    //noinspection HardcodedFileSeparator
    private static final String REGEX_HEX_GRADIENT = "<#([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])>(.*?)<#/([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])>";
    private static final Pattern PATTERN_HEX_GRADIENT = Pattern.compile(REGEX_HEX_GRADIENT);

    private static final String REGEX_AMPERSAND_HASH = "&#(" + REGEX_HEX + ")";
    private static final Pattern PATTERN_AMPERSAND_HASH = Pattern.compile(REGEX_AMPERSAND_HASH);
    private static final String REGEX_XML_LIKE_HASH = "<#(" + REGEX_HEX + ")>";
    private static final Pattern PATTERN_XML_LIKE_HASH = Pattern.compile(REGEX_XML_LIKE_HASH);
    private static AtomicReference<Plugin> itemsAdderPlugin;
    private static AtomicReference<Plugin> placerholderApiPlugin;

    /**
     * Prints a banner / headline to console
     *
     * @param text text to be printed
     */
    public static void banner(final CharSequence text) {
        if(JeffLib.getPlugin() == null) {
            throw new JeffLibNotInitializedException();
        }
        final int bannerWidth = Math.max(text.length() + 4, MIN_BANNER_WIDTH);
        StringUtils.leftPad(EMPTY, bannerWidth, BANNER_CHAR);
        JeffLib.getPlugin().getLogger().info(StringUtils.center(" " + text + " ", bannerWidth, BANNER_CHAR));
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
     * Replaces color codes using &. Also supports hex colors using <pre>&x&r&r&g&g&b&b</pre>, <pre>&#rrggbb</pre> and <pre><#rrggbb></pre>,
     * and gradients using <pre><#rrggbb> <#/rrggbb> [<#/rrggbb> ...]</pre>
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

        text = text.replaceAll("<#/([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])>", "<#/$1><#$1>");

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
        while (result.matches(".*&x&[0-9a-zA-Z]&[0-9a-zA-Z]&[0-9a-zA-Z]&[0-9a-zA-Z]&[0-9a-zA-Z]&[0-9a-zA-Z]$")) {
            result = result.substring(0, result.length() - 14);
        }
        return result;
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
     * Replaces Emojis, PlacederholderAPI placeholders and colors (see {@link #color(String)})
     *
     * @param text   Text to translate
     * @param player Player to apply placeholders for, or null
     * @return Translated text
     */
    public static String format(String text, @Nullable final OfflinePlayer player) {
        text = color(text);
        text = replaceEmojis(text);
        text = replacePlaceholders(text, player);
        return text;
    }

    public static List<String> format(List<String> text, @Nullable final OfflinePlayer player) {
        text = new ArrayList<>(text);
        for(int i = 0; i < text.size(); i++) {
            text.set(i, format(text.get(i)));
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
        if (placerholderApiPlugin == null) {
            placerholderApiPlugin = new AtomicReference<>(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"));
        }
        if (placerholderApiPlugin.get() != null) {
            text = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

}
