package ch.heig.dil.files;

import ch.heig.dil.parsers.MarkdownParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilesHelperTest {

    @Test
    void fileDoesNotExist() {
        assertThrows(
                Exception.class,
                () -> {
                    FilesHelper.readFromFile("");
                });
    }

    //
    @Test
    void readFromAbsoluteFile(){
        assertEquals(
                "Je suis un texte\n",
                FilesHelper.readFromFile("C:\\Users\\Jonathan\\HEIG\\SEMESTRE_4\\DIL\\Labo2\\projet-friedli_jaquier_kaelin_pavicevic\\src\\test\\java\\ch\\heig\\dil\\files\\alo.txt"));
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
}
