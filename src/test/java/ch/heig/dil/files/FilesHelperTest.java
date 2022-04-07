package ch.heig.dil.files;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

public class FilesHelperTest {
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
}
