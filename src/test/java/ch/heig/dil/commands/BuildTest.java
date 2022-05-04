package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.*;

import ch.heig.dil.files.FilesHelper;
import java.io.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class BuildTest {
    private static final String INIT_FOLDER = "./test-build";
    private static final String BUILD_FOLDER = "build";

    /** Crée le dossier de test avant chaque test */
    @BeforeEach
    protected void setup() {
        try {
            FilesHelper.deleteDirectory(BUILD_FOLDER);
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
        FilesHelper.deleteDirectory(BUILD_FOLDER);
    }

    @Test
    void testNormalBuildBehaviour() {
        String[] args = new String[] {INIT_FOLDER};
        CommandLine cmd = new CommandLine(new Build());
        cmd.execute(args);

        assertTrue(new File(BUILD_FOLDER + "/index.html").exists());
        assertTrue(new File(BUILD_FOLDER + "/pages/image.jpeg").exists());
        assertTrue(new File(BUILD_FOLDER + "/pages/page.html").exists());
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
        String[] args = new String[] {"invalid-path-to-static-website"};
        CommandLine cmd = new CommandLine(new Build());
        cmd.execute(args);
        assertThrows(IOException.class, () -> cmd.execute(args));
    }
}
