package ch.heig.dil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.heig.dil.files.FilesHelper;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class BoomshotTest {

    @Test
    void result() {
        int exitCode = new CommandLine(new Boomshot()).execute();
        assertEquals(exitCode, 0);
    }

    @Test
    void exception() {
        assertThrows(
                Exception.class,
                () -> {
                    throw new Exception();
                });
    }

    @Test
    void output() throws Exception {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            new CommandLine(new Boomshot()).execute();
            assertTrue((output.toString().contains("A static site generator")));
        }
    }

    @Test
    void testGetVersion() throws Exception {
        final String OPEN_TAG = "<version>";
        final String CLOSING_TAG = "</version>";
        Boomshot.VersionProvide versionProvide = new Boomshot.VersionProvide();
        String pomContent = FilesHelper.readFromFile("pom.xml");
        String configVersion =
                pomContent.substring(
                        pomContent.indexOf(OPEN_TAG) + OPEN_TAG.length(),
                        pomContent.indexOf(CLOSING_TAG));
        String[] rawVersion = versionProvide.getVersion();
        String[] generatedVersion = rawVersion[0].split(" ");
        assertEquals(version[0], "Boomshot");

        assertEquals(generatedVersion[1], configVersion);
    }
}
