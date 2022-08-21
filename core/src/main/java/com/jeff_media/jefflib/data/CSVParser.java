/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.data;

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