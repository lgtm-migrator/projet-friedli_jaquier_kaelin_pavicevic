package ch.heig.dil.files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

/**
 * Classe qui permet de lire un fichier ou d'écrire dans un fichier
 *
 * @author Valentin Kaelin, Jonathan Friedli
 */
public class FilesHelper {

    /**
     * Lit un fichier
     *
     * @param path chemin complet du fichier
     * @return une string du contenu du fichier
     * @throws IOException si le fichier n'existe pas
     */
    public static String readFromFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File does not exist !");
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

    /**
     * Crée un fichier avec un contenu
     *
     * @param path chemin du fichier qui va être créé
     * @param content Contenu du fichier
     * @throws IOException si le fichier existe déjà
     */
    public static void createFile(String path, String content) throws IOException {
        Path file = Paths.get(path);
        if (Files.exists(file)) {
            throw new IOException("File already exists !");
        }
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyDirectory(String sourceLocation, String destinationLocation)
            throws IOException {
        Files.walk(Paths.get(sourceLocation))
                .forEach(
                        source -> {
                            Path destination =
                                    Paths.get(
                                            destinationLocation,
                                            source.toString().substring(sourceLocation.length()));
                            try {
                                Files.copy(source, destination);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
    }

    public static void deleteDirectory(String directory) throws IOException {
        Files.walk(Paths.get(directory))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
