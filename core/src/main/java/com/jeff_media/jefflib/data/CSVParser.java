package com.jeff_media.jefflib.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private final List<String[]> list = new ArrayList<>();

    public CSVParser(java.io.InputStream inputStream) {
        try (
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            bufferedReader.lines().forEachOrdered(line -> {
                list.add(line.split(REGEX));
            });
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public List<String[]> getContents() {
        return list;
    }

}
