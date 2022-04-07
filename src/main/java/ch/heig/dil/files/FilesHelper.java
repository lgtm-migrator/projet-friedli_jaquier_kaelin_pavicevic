package ch.heig.dil.files;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Classe qui permet de lire un fichier ou d'écrire dans un fichier
 * @author Valentin Kaelin, Jonathan Friedli
 */
public class FilesHelper {

    public static String readFromFile(String path) throws RuntimeException {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("File does not exist !");
        }

        StringBuilder body = new StringBuilder();
        try (BufferedReader bufferedReader =
                     new BufferedReader(
                             new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
                body.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }
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
