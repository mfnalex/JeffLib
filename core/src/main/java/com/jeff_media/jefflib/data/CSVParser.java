package com.jeff_media.jefflib.data;
import org.bukkit.Material;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Redempt
 */
public class CSVParser {

    public CSVParser(InputStream stream) {
    }

    public static List<String[]> parse(String input) {
        return new CSVParser(input).file();
    }

    private String str;
    private int pos;
    private StringBuilder builder = new StringBuilder();

    public CSVParser(String str) {
        this.str = str;
    }

    private char peek() {
        return str.charAt(pos);
    }

    private char advance() {
        return str.charAt(pos++);
    }

    private List<String[]> file() {
        List<String[]> out = new ArrayList<>();
        comment();
        String[] headers = line();
        out.add(headers);
        while (pos < str.length()) {
            out.add(line(headers.length));
        }
        return out;
    }

    private void comment() {
        if (peek() != '#') {
            return;
        }
        while (advance() != '\n') {}
    }

    private String[] line() {
        List<String> out = new ArrayList<>();
        do {
            out.add(next());
        } while (pos < str.length() && advance() == ',');
        return out.toArray(new String[0]);
    }

    private String[] line(int length) {
        if (pos >= str.length()) {
            return null;
        }
        String[] out = new String[length];
        int num = 0;
        for (int i = 0; i < length; i++) {
            out[num++] = next();
            pos++; // Skip comma
        }
        return out;
    }

    private boolean isSeparator(char c) {
        return c == ',' || c == '\n';
    }

    private String next() {
        builder.setLength(0);
        if (peek() == '"') {
            return string();
        }
        while (pos < str.length() && !isSeparator(peek())) {
            builder.append(advance());
        }
        return builder.toString();
    }

    private char escapeSequence() {
        switch (advance()) {
            case 'n':
                return '\n';
            case 't':
                return '\t';
            case 'r':
                return '\r';
            case 'u':
                return (char) Integer.parseInt(str.substring(pos, pos += 4), 16);
            case '"':
                return '"';
            case '\\':
                return '\\';
            default:
                throw new IllegalArgumentException("Invalid escape sequence at position " + pos);
        }
    }

    private String string() {
        advance();
        char c;
        while ((c = advance()) != '"') {
            builder.append(c == '\\' ? escapeSequence() : c);
        }
        return builder.toString();
    }

}