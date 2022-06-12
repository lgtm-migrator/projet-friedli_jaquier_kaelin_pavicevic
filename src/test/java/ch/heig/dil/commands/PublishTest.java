package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.heig.dil.Boomshot;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class PublishTest {

    @Test
    void exitedAfterMissignArgs() {
        int exitCode = new CommandLine(new Boomshot()).execute("publish");
        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }
}
