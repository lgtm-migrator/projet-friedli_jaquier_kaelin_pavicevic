package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.codehaus.plexus.util.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class InitTest {
    private static final String FOLDER_NAME = "./test-init";

    @Test
    void shouldCreateTheDirectory() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);

        assertTrue(new File(FOLDER_NAME).exists());

        FileUtils.deleteDirectory(new File(FOLDER_NAME));
    }

    @Test
    void shouldCreateTheConfigFile() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);

        assertTrue(new File(FOLDER_NAME + "/config.yml").exists());

        FileUtils.deleteDirectory(new File(FOLDER_NAME));
    }
}
