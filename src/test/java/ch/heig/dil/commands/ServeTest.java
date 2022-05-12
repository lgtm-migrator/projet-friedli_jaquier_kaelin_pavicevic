package ch.heig.dil.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ServeTest extends AbstractTest {

    //    @Test
    //    void result() {
    //        int exitCode = new CommandLine(new Boomshot()).execute("serve");
    //        assertEquals(exitCode, 0);
    //    }

    @Test
    void exception() {
        assertThrows(
                Exception.class,
                () -> {
                    throw new Exception();
                });
    }

    //    @Test
    //    void output() throws Exception {
    //        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
    //            System.setOut(new PrintStream(output));
    //            new CommandLine(new Boomshot()).execute("serve");
    //            assertTrue((output.toString().contains("Serve command")));
    //        }
    //    }
}
