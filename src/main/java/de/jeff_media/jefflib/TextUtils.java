package de.jeff_media.jefflib;

import org.apache.commons.lang3.StringUtils;

/**
 * Provides some methods to print nice formatted texts/banners to console
 */
public class TextUtils {

    private static final int MIN_BANNER_WIDTH = 30;
    private static final char BANNER_CHAR = '#';
    private static final char SPACE = ' ';
    private static final String EMPTY = "";

    /**
     * Prints a banner / headline to console
     * @param text text to be printed
     */
    public static void banner(String text) {
        final int bannerWidth = Math.max(text.length()+4,MIN_BANNER_WIDTH);
        StringUtils.leftPad(EMPTY,bannerWidth,BANNER_CHAR);
        System.out.println(StringUtils.center(SPACE + text + SPACE,bannerWidth, BANNER_CHAR));
    }
}
