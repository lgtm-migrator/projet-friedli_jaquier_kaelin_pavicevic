package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.heig.dil.Boomshot;
import ch.heig.dil.files.FilesHelper;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class ServeTest extends AbstractTest {

    private static final String INIT_NAME = "test-build";
    private static final String INIT_FOLDER = "./test-build";

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
        cmd = new CommandLine(new Build());
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
    void exitedAfterMissignDirectory() {
        int exitCode = new CommandLine(new Boomshot()).execute("serve");
        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void exitedAfterBadDirectory() {
        int exitCode = new CommandLine(new Boomshot()).execute("serve", "fakeDirectory");
        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void exitedAfterBadPort() {
        int exitCode =
                new CommandLine(new Boomshot()).execute("serve", INIT_NAME, "--port", "fakePort");
        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void throwsIllegalArgumentExceptionAfterNegativePort() {
        int exitCode =
                new CommandLine(new Boomshot()).execute("serve", INIT_NAME, "--port", "-1000");
        assertEquals(-1, exitCode);
    }
}
