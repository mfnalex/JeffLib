package de.jeff_media.jefflib;

import org.bukkit.ChatColor;

public class HexColor {

    public int r, g, b;

    public HexColor(int r, int g, int b) {
        this.r=r;
        this.g=g;
        this.b=b;
    }

    public HexColor(String hex) {
        this(hex.substring(0,2),hex.substring(2,4), hex.substring(4,6));
    }

    public HexColor(String r, String g, String b) {
        this.r=Integer.parseInt(r,16);
        this.g=Integer.parseInt(g,16);
        this.b=Integer.parseInt(b,16);
    }

    public static int getSingleValueAtPositionInGradient(int start, int end, int colorsNeeded, int position) {
        if(position==0) return start;
        if(position==colorsNeeded) return end;
        if(start == end) return start;
        if(start < end) return (start + end) / colorsNeeded * position;
        return start + end - ((start - end) / colorsNeeded * position);
    }

    public static HexColor getHexAtPositionInGradient(HexColor start, HexColor end, int textLengh, int position) {
        //System.out.println("Position " + position);
        if(position==0) return start;
        if(position==textLengh-1) return end;
        int colorsNeeded = textLengh-1;
        int r = getSingleValueAtPositionInGradient(start.r, end.r, colorsNeeded, position);
        int g = getSingleValueAtPositionInGradient(start.g, end.g, colorsNeeded, position);
        int b = getSingleValueAtPositionInGradient(start.b, end.b, colorsNeeded, position);

        HexColor result = new HexColor(r,g,b);
        //System.out.println(result.toHex());
        return result;
    }

    public String toHex() {
        return String.format("%02x",r)
                + String.format("%02x",g)
                + String.format("%02x",b);
    }

    public String toColorCode() {
        StringBuilder sb = new StringBuilder("&x");
        char[] chars = toHex().toCharArray();
        for(Character aChar : chars) {
            sb.append('&').append(aChar);
        }
        return sb.toString();
    }

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
}
