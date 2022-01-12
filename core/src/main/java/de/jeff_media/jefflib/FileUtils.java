package de.jeff_media.jefflib;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public static void writeToFile(final File file, final Iterable<String> lines) {
        try {
            final Writer output = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8);
            for (final String line : lines) {
                output.append(line).append(System.lineSeparator());
            }
            output.close();
        } catch (final IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Appends the given line to the file
     *
     * @param file File to append to
     * @param line line to append
     */
    public static void appendLines(final File file, final String line) {
        appendLines(file, new String[]{line});
    }

    /**
     * Appends the given lines to the file
     *
     * @param file  File to append to
     * @param lines lines to append
     */
    public static void appendLines(final File file, final String[] lines) {
        try {
            final Writer output = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
            for (final String line : lines) {
                output.append(line).append(System.lineSeparator());
            }
            output.close();
        } catch (final IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Appends the given lines to the file
     *
     * @param file  File to append to
     * @param lines lines to append
     */
    public static void appendLines(final File file, final Collection<String> lines) {
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
        try(InputStream input = plugin.getResource(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
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
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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
            // write the new string with the replaced line OVER the same file
            final FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
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
        File file = JeffLib.getPlugin().getDataFolder();
        for (final String s : path) {
            file = new File(file, s);
        }
        return file;
    }
}
