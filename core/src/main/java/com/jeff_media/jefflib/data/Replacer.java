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

import java.util.Map;

/**
 * Replaces placeholders in a string
 */
public class Replacer {

    private final Map<String, String> map = new java.util.HashMap<>();

    /**
     * Adds a new placeholder
     */
    public Replacer put(final String key, final String value) {
        map.put(key, value);
        return this;
    }

    /**
     * Applies all given placeholders
     */
    public String apply(final String input) {
        String output = input;
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            output = output.replace(entry.getKey(), entry.getValue());
        }
        return output;
    }

}
