package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.*;

import ch.heig.dil.files.FilesHelper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class BuildTest {
    private static final String INIT_FOLDER = "./test-build";
    private static final String BUILD_FOLDER = INIT_FOLDER + "/build";

    /** Crée le dossier de test avant chaque test */
    @BeforeEach
    protected void setup() {
        try {
            FilesHelper.deleteDirectory(INIT_FOLDER);
        } catch (Exception ignored) {
        }
        String[] args = new String[] {INIT_FOLDER};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);
    }

    /**
     * Supprime le dossier de test après chaque test
     *
     * @throws IOException si une erreur apparait lors de la suppression du dossier
     */
    @AfterEach
    protected void clean() throws IOException {
        FilesHelper.deleteDirectory(INIT_FOLDER);
    }

    @Test
    void testNormalBuildBehaviour() throws IOException {
        String[] args = new String[] {INIT_FOLDER};
        CommandLine cmd = new CommandLine(new Build());
        cmd.execute(args);

        assertTrue(new File(BUILD_FOLDER + "/index.html").exists());
        assertTrue(new File(BUILD_FOLDER + "/pages/image.jpeg").exists());
        assertTrue(new File(BUILD_FOLDER + "/pages/page.html").exists());
        assertTrue(
                Files.readString(Path.of(BUILD_FOLDER + "/index.html"))
                        .contains("<title>My website | Homepage </title>"));
        assertTrue(
                Files.readString(Path.of(BUILD_FOLDER + "/index.html"))
                        .contains("<h1>My title</h1>"));
    }

    @Test
    void testBuildIfAlreadyExist() {
        String[] args = new String[] {INIT_FOLDER};
        CommandLine cmd = new CommandLine(new Build());
        cmd.execute(args);
        cmd.execute(args);

        assertTrue(new File(BUILD_FOLDER + "/index.html").exists());
        assertTrue(new File(BUILD_FOLDER + "/pages/image.jpeg").exists());
        assertTrue(new File(BUILD_FOLDER + "/pages/page.html").exists());
    }

    @Test
    void testBuildOnNotInitializedWebsite() {
        String invalidPath = "invalid-path-to-static-website";
        String[] args = new String[] {invalidPath};
        CommandLine cmd = new CommandLine(new Build());
        int exitCode = cmd.execute(args);
        assertFalse(new File(invalidPath).exists());
        assertEquals(exitCode, CommandLine.ExitCode.SOFTWARE);
    }
}
