package de.jeff_media.jefflib;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides some text related methods
 */
public class TextUtils {

    private static final int MIN_BANNER_WIDTH = 30;
    private static final char BANNER_CHAR = '#';
    private static final char SPACE = ' ';
    private static final String EMPTY = "";
    private static final String AMPERSAND_HASH_REGEX = "&#([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])";
    private static final Pattern AMPERSAND_HASH_PATTERN = Pattern.compile(AMPERSAND_HASH_REGEX);
    private static final String XML_LIKE_HASH_REGEX = "<#([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])>";
    private static final Pattern XML_LIKE_HASH_PATTERN = Pattern.compile(XML_LIKE_HASH_REGEX);
    private static AtomicReference<Plugin> itemsAdderPlugin = null;
    private static AtomicReference<Plugin> placerholderApiPlugin = null;

    /**
     * Prints a banner / headline to console
     *
     * @param text text to be printed
     */
    public static void banner(String text) {
        final int bannerWidth = Math.max(text.length() + 4, MIN_BANNER_WIDTH);
        StringUtils.leftPad(EMPTY, bannerWidth, BANNER_CHAR);
        System.out.println(StringUtils.center(SPACE + text + SPACE, bannerWidth, BANNER_CHAR));
    }

    private static String addAmpersandsToHex(String hex) {
        if (hex.length() != 6) {
            throw new IllegalArgumentException("Hex-Codes must always have 6 letters.");
        }
        char[] chars = hex.toCharArray();
        StringBuilder sb = new StringBuilder("&x");
        for (char aChar : chars) {
            sb.append("&").append(aChar);
        }
        return sb.toString();
    }

    private static String replaceRegexWithGroup(String text, Pattern pattern, int group, Function<String, String> function) {
        Matcher matcher = pattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, function.apply(matcher.group(group)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String replaceEmojis(String text) {
        if (itemsAdderPlugin == null) {
            itemsAdderPlugin = new AtomicReference<>(Bukkit.getPluginManager().getPlugin("ItemsAdder"));
        }
        if(itemsAdderPlugin.get()!=null) {
            text = dev.lone.itemsadder.api.FontImages.FontImageWrapper.replaceFontImages(text);
        }
        return text;
    }

    /**
     * Replaces color codes using &. Also supports hex colors using &x&r&r&g&g&b&b, &#rrggbb and <#rrggbb>
     *
     * @param text
     * @return
     */
    public static String color(String text) {
        text = replaceRegexWithGroup(text, AMPERSAND_HASH_PATTERN, 1, TextUtils::addAmpersandsToHex);
        text = replaceRegexWithGroup(text, XML_LIKE_HASH_PATTERN, 1, TextUtils::addAmpersandsToHex);
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String format(String text) {
        return format(text, null);
    }

    public static String format(String text, @Nullable OfflinePlayer player) {
        text = color(text);
        text = replaceEmojis(text);
        text = replacePlaceholders(text, player);
        return text;
    }

    public static String replacePlaceholders(String text, @Nullable OfflinePlayer player) {
        if (placerholderApiPlugin == null) {
            placerholderApiPlugin = new AtomicReference<>(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"));
        }
        if(placerholderApiPlugin.get()!=null) {
            text = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }
}
