package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.heig.dil.files.FilesHelper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

        FilesHelper.deleteDirectory(FOLDER_NAME);
    }

    @Test
    void shouldCreateTheConfigFile() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);

        assertTrue(new File(FOLDER_NAME + "/config.yml").exists());

        FilesHelper.deleteDirectory(FOLDER_NAME);
    }

    @Test
    void shouldCreateIndexFile() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);

        assertTrue(new File(FOLDER_NAME + "/index.md").exists());

        FilesHelper.deleteDirectory(FOLDER_NAME);
    }

    @Test
    void shouldCreateSubPages() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);

        assertTrue(new File(FOLDER_NAME + "/pages/page.md").exists());

        FilesHelper.deleteDirectory(FOLDER_NAME);
    }

    @Test
    void shouldCreateImages() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);

        assertTrue(new File(FOLDER_NAME + "/pages/image.jpeg").exists());

        FilesHelper.deleteDirectory(FOLDER_NAME);
    }
    
    @Test
    void shouldCreateTemplateFiles() throws IOException {
        String[] args = new String[] {FOLDER_NAME};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);
    
        assertTrue(new File(FOLDER_NAME + "/template/layout.hbs").exists());
        assertTrue(new File(FOLDER_NAME + "/template/menu.hbs").exists());
        
        FilesHelper.deleteDirectory(FOLDER_NAME);
    }
}
