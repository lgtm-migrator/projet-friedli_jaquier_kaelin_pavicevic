package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ch.heig.dil.Boomshot;
import java.io.*;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class CleanTest {

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

        int exitCode = cmd.execute("clean", "/CleanTest");
        boolean fileExist = Files.exists(path.toPath());

        File site = new File("CleanTest");
        site.delete();
        assertFalse(fileExist);
        assertEquals(0, exitCode);
    }
}
