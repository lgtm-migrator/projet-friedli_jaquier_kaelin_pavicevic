package ch.heig.dil.files;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Classe qui permet de tester la lecture d'un fichier ou l'écriture dans un fichier
 *
 * @author Valentin Kaelin, Jonathan Friedli
 */
public class FilesHelperTest {

    @Test
    void fileDoesNotExist() {
        assertThrows(
                Exception.class,
                () -> {
                    FilesHelper.readFromFile("");
                });
    }

    @Test
    void fileIsCreated() {
        String path = "tmp/test/test.txt";
        String content = "Ceci est un test!";
        assertDoesNotThrow(() -> FilesHelper.createFile(path, content));

        File created = new File(path);
        assertTrue(created.exists());

        if (!created.delete()) {
            throw new RuntimeException("Error while deleting file in test.");
        }
    }

    @Test
    void fileAlreadyExists() {
        String path = "test.txt";
        String content = "Ceci est un test!";
        assertDoesNotThrow(() -> FilesHelper.createFile(path, content));
        assertThrows(IOException.class, () -> FilesHelper.createFile(path, content));

        File created = new File(path);
        assertTrue(created.exists());

        if (!created.delete()) {
            throw new RuntimeException("Error while deleting file in test.");
        }
    }

    @Test
    void writeAndRead() {
        String path = "test.txt";
        String content = "Ceci est un test!";
        assertDoesNotThrow(() -> FilesHelper.createFile(path, content));
        File created = new File(path);
        String result = assertDoesNotThrow(() -> FilesHelper.readFromFile(path));
        assertEquals("Ceci est un test!\n", result);

        if (!created.delete()) {
            throw new RuntimeException("Error while deleting file in test.");
        }
    }
}
