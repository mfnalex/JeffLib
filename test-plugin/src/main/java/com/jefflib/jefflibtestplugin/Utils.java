package com.jefflib.jefflibtestplugin;

public interface Utils {

    static String repeat(String string, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(string);
        }
        return sb.toString();
    }

}
