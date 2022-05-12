package ch.heig.dil.commands;

import java.io.OutputStream;
import java.io.PrintStream;

public class AbstractTest {
    private final PrintStream originalStream = System.out;
    private final PrintStream hiddenStream =
            new PrintStream(
                    new OutputStream() {
                        public void write(int b) {
                            // NO-OP
                        }
                    });

    public void setHiddenStream() {
        System.setErr(hiddenStream);
    }

    public void resetDefaultStream() {
        System.setErr(originalStream);
    }
}
