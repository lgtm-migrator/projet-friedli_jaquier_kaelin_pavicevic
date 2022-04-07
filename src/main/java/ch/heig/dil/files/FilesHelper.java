package ch.heig.dil.files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesHelper {
    public static void createFile(String path, String content) throws IOException {
        Path file = Paths.get(path);
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
