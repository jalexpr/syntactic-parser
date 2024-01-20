package ru.textanalysis.tawt.sp.rules.controlmodel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class FileWorker {

    public static Set<String> readFileFromPath(String path) {
        Set<String> wordsSet = new HashSet<>();
        try (InputStream inputStream = FileWorker.class.getClassLoader().getResourceAsStream(path)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String cleanLine = line.replaceAll("[0-9.\\s]+", "");
                    if (!cleanLine.isEmpty()) {
                        wordsSet.add(cleanLine);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsSet;
    }

    public static boolean isTxtFile(String filename) {
        return filename != null && filename.endsWith(".txt");
    }
}
