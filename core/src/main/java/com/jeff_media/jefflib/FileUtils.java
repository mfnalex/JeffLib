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

package com.jeff_media.jefflib;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * File related methods
 */
@UtilityClass
public final class FileUtils {

    /**
     * Replaces or creates a file with the given content
     *
     * @param file  File to write to
     * @param lines lines to write
     */
    public static void writeToFile(final File file, final Iterable<String> lines) throws IOException {
        try (final Writer output = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8)) {
            for (final String line : lines) {
                output.append(line).append(System.lineSeparator());
            }
        }
    }

    /**
     * Appends the given line to the file
     *
     * @param file File to append to
     * @param line line to append
     */
    public static void appendLines(final File file, final String line) throws IOException {
        appendLines(file, new String[]{line});
    }

    /**
     * Appends the given lines to the file
     *
     * @param file  File to append to
     * @param lines lines to append
     */
    public static void appendLines(final File file, final String[] lines) throws IOException {
        try (final Writer output = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8)) {
            for (final String line : lines) {
                output.append(line).append(System.lineSeparator());
            }
        }
    }

    /**
     * Appends the given lines to the file
     *
     * @param file  File to append to
     * @param lines lines to append
     */
    public static void appendLines(final File file, final Collection<String> lines) throws IOException {
        appendLines(file, lines.toArray(new String[0]));
    }

    /**
     * Reads a file from the resources directory and returns it as List of Strings
     *
     * @param plugin   Plugin
     * @param fileName File name
     * @return A list of Strings of the file's contents
     */
    @SneakyThrows
    public static List<String> readFileFromResources(final Plugin plugin, final String fileName) {
        try (final InputStream input = plugin.getResource(fileName); final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(input)))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    /**
     * Replaces strings in a file line by line.
     *
     * @param file     File
     * @param toSearch String to search for
     * @param replace  String for replacement
     * @return true if the file has been changed, otherwise false
     */
    public static boolean replaceStringsInFile(final File file, final CharSequence toSearch, final CharSequence replace) throws IOException {
        boolean changed = false;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            final StringBuilder inputBuffer = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(toSearch)) {
                    line = line.replace(toSearch, replace);
                    changed = true;
                }
                inputBuffer.append(line);
                inputBuffer.append(System.lineSeparator());
            }
            bufferedReader.close();
            if (changed) {
                try (final FileOutputStream fileOut = new FileOutputStream(file)) {
                    fileOut.write(inputBuffer.toString().getBytes());
                }
            }
        }
        return changed;
    }

    /**
     * Gets a file in the data folder
     *
     * @param path Path as array, e.g. {"mainDirectory","subDirectory","fileName"}
     * @return the File
     */
    public static File getFile(final String... path) {
        // JeffLibNotInitializedException.check();
        File file = JeffLib.getPlugin().getDataFolder();
        for (final String s : path) {
            file = new File(file, s);
        }
        return file;
    }

    /**
     * Saves a resource to the data folder if it doesn't already exist.
     * @return true if the file has been created, otherwise false
     */
    public static boolean saveResourceIfNotExists(final String filename) {
        final File file = new File(JeffLib.getPlugin().getDataFolder(), filename);
        if (!file.exists()) {
            JeffLib.getPlugin().saveResource(filename, false);
            return true;
        }
        return false;
    }
}
