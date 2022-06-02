package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.*;

import ch.heig.dil.Boomshot;
import ch.heig.dil.files.FilesHelper;
import java.io.*;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class CleanTest extends AbstractTest {

    @Test
    void testClean() {
        String dirName = "CleanTest/build";
        assertFalse(Files.exists(new File("CleanTest").toPath()));
        new File(dirName).mkdirs();

        // Delete the previously created build folder by using the clean command
        Boomshot app = new Boomshot();
        StringWriter sw = new StringWriter();
        CommandLine cmd = new CommandLine(app);
        cmd.setOut(new PrintWriter(sw));

        File path = new File("CleanTest/build");
        assertDoesNotThrow(
                () -> FilesHelper.createFile("CleanTest/build/test.txt", "temporaryFile"));
        int exitCode = cmd.execute("clean", "CleanTest");
        File site = new File("CleanTest");
        site.delete();
        assertFalse(Files.exists(site.toPath()) || Files.exists(path.toPath()));
        assertEquals(0, exitCode);
    }
}
