package ch.heig.dil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;

import ch.heig.dil.files.FilesHelper;
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
        Boomshot.VersionProvide versionProvide = new Boomshot.VersionProvide();
        String pomContent = FilesHelper.readFromFile("pom.xml");
        assertTrue(pomContent != null);
        String testVers = pomContent.substring(pomContent.indexOf("<version>") + 9,
                pomContent.indexOf("</version>"));
        String[] rawVersion = versionProvide.getVersion();
        String[] version = rawVersion[0].split(" ");
        assertEquals(version[0], "Boomshot");
        
        assertEquals(version[1], testVers);
    }
}
