package de.jeff_media.jefflib;


import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    public static void appendLines(final File file, final String line) {
        appendLines(file, new String[] {line});
    }

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

    public static List<String> readFileFromResources(JavaPlugin plugin, final String fileName) {
        final InputStream input = plugin.getResource(fileName);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        final List<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        return lines;
    }

    /**
     * Replaces strings in a file line by line.
     *
     * @param file     File
     * @param toSearch String to search for
     * @param replace  String for replacement
     * @return true if the file has been changed, otherwise false
     */
    public static boolean replaceStringsInFile(final File file, final String toSearch, final String replace) throws IOException {

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
            inputBuffer.append('\n');
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
}
