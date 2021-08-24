package de.jeff_media.jefflib;

import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;

/**
 * Represents a hex color code
 */
public class HexColor {

    private static final String REGEX_COLOR_COMPONENT = "[0-9a-zA-Z][0-9a-zA-Z]";

    private int r;
    private int g;
    private int b;

    /**
     * Creates a HexColor with the given RGB value
     * @param r Red (0-255)
     * @param g Green (0-255)
     * @param b Blue (0-255)
     */
    @SneakyThrows
    public HexColor(int r, int g, int b) {
        Validate.inclusiveBetween(0,255,r);
        Validate.inclusiveBetween(0,255,g);
        Validate.inclusiveBetween(0,255,b);
        this.setR(r);
        this.setG(g);
        this.setB(b);
    }

    /**
     * Creates a HexColor from a String in the format "rrggbb" (e.g. 00ff00)
     * @param hex Color in hex
     */
    public HexColor(String hex) {
        this(hex.substring(0,2),hex.substring(2,4), hex.substring(4,6));
    }

    /**
     * Creates a HexColor from three separate hex color Strings
     * @param r Red (00-ff)
     * @param g Green (00-ff)
     * @param b Blue (00-ff)
     */
    public HexColor(String r, String g, String b) {
        Validate.matchesPattern(r,REGEX_COLOR_COMPONENT);
        Validate.matchesPattern(g,REGEX_COLOR_COMPONENT);
        Validate.matchesPattern(b,REGEX_COLOR_COMPONENT);
        this.setR(Integer.parseInt(r,16));
        this.setG(Integer.parseInt(g,16));
        this.setB(Integer.parseInt(b,16));
    }

    /**
     * Returns the value for a specific color component at a given location inside a gradient
     * @param start Color component value at the gradient's start
     * @param end Color component value at the gradient's end
     * @param colorsNeeded The amount of different colors needed for this gradient. This is usually the text length - 1
     * @param position Position to get the color component value for
     * @return Color component value at the given position (0-255)
     */
    private static int getSingleValueAtPositionInGradient(int start, int end, int colorsNeeded, int position) {
        if(position==0) return start;
        if(position==colorsNeeded) return end;
        if(start == end) return start;
        if(start < end) return (start + end) / colorsNeeded * position;
        return start + end - ((start - end) / colorsNeeded * position);
    }

    /**
     * Returns the HexColor for a given gradient at the given position
     * @param start Starting color
     * @param end End color
     * @param textLengh Text lengh
     * @param position Position inside the text
     * @return HexColor needed at this position
     */
    public static HexColor getHexAtPositionInGradient(HexColor start, HexColor end, int textLengh, int position) {
        //System.out.println("Position " + position);
        if(position==0) return start;
        if(position==textLengh-1) return end;
        int colorsNeeded = textLengh-1;
        int r = getSingleValueAtPositionInGradient(start.getR(), end.getR(), colorsNeeded, position);
        int g = getSingleValueAtPositionInGradient(start.getG(), end.getG(), colorsNeeded, position);
        int b = getSingleValueAtPositionInGradient(start.getB(), end.getB(), colorsNeeded, position);

        HexColor result = new HexColor(r,g,b);
        //System.out.println(result.toHex());
        return result;
    }

    /**
     * Returns the HexColor as a String in the format rrggbb (e.g. 00ff00)
     * @return HexColor as String
     */
    public String toHex() {
        return String.format("%02x", getR())
                + String.format("%02x", getG())
                + String.format("%02x", getB());
    }

    /**
     * Converts the HexColor into color codes readable by {@link net.md_5.bungee.api.ChatColor#translateAlternateColorCodes(char, String)}, using the ampersand (&) as alternate color character
     * @return HexColor as color code, e.g. "&x&0&0&f&f&0&0"
     */
    public String toColorCode() {
        StringBuilder sb = new StringBuilder("&x");
        char[] chars = toHex().toCharArray();
        for(Character aChar : chars) {
            sb.append('&').append(aChar);
        }
        return sb.toString();
    }

    /**
     * Applies a gradient to the given text. Also preserves already existing formatting codes inside the text.
     * @param text Text to apply gradient to
     * @param start Starting color
     * @param end End color
     * @return Text with color codes applied to match the given gradient
     */
    public static String applyGradient(String text, HexColor start, HexColor end) {
        char[] chars = text.toCharArray();
        int length = text.length();
        StringBuilder sb = new StringBuilder();
        String nextFormat = "";
        //System.out.println("Applying gradient");
        //System.out.println("  Text: " + text);
        //System.out.println("  Colors: " + start.toHex() + " > " + end.toHex());
        for(int i = 0; i < length; i++) {
            //System.out.println("Current char: " + chars[i]);
            if(nextFormat.length() % 2 == 1) {
                if(chars[i] == 'r' || chars[i] == 'R') {
                    //System.out.println("Formatting cleared");
                    sb.append(ChatColor.translateAlternateColorCodes('&',"&r"));
                    nextFormat = "";
                    continue;
                }
                //System.out.println("Found format: " + chars[i]);
                nextFormat = nextFormat + chars[i];
                continue;
            }
            if(chars[i] == '&' || chars[i] == '§') {
                nextFormat = nextFormat + "&";
                continue;
            }
            //System.out.println("Next formatting: " + nextFormat);
            sb.append(getHexAtPositionInGradient(start, end, length, i).toColorCode()).append(ChatColor.translateAlternateColorCodes('&',nextFormat)).append(chars[i]);
            //nextFormat="";
        }
        return sb.toString();
    }

    /**
     * Returns the Red color component value
     * @return Red color component value (0-255)
     */
    public int getR() {
        return r;
    }


    /**
     * Sets the Red color component value
     * @param r Red color component value (0-255)
     */
    public void setR(int r) {
        Validate.inclusiveBetween(0,255,r);
        this.r = r;
    }

    /**
     * Returns the Green color component value
     * @return Red green component value (0-255)
     */
    public int getG() {
        return g;
    }

    /**
     * Sets the Green color component value
     * @param g Green color component value (0-255)
     */
    public void setG(int g) {
        Validate.inclusiveBetween(0,255,g);
        this.g = g;
    }

    /**
     * Returns the Blue color component value
     * @return Blue color component value (0-255)
     */
    public int getB() {
        return b;
    }

    /**
     * Sets the Blue color component value
     * @param b Blue color component value (0-255)
     */
    public void setB(int b) {
        Validate.inclusiveBetween(0,255,b);
        this.b = b;
    }
}
