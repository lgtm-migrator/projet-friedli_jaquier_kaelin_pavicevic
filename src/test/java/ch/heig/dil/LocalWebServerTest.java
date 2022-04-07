package ch.heig.dil;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class LocalWebServerTest {

    @Test
    void ConstructorDoesntThrow() {
        var server = assertDoesNotThrow(() -> new LocalWebServer(4242));
        server.stop();
    }

    @Test
    void WebServerIsListening() throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            var server = assertDoesNotThrow(() -> new LocalWebServer(4242));
            assertTrue((output.toString().contains("Listening on port 4242")));
            server.stop();
        }
    }

    @Test
    void WebServerIsListeningOnDefaultPort() throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            var server = assertDoesNotThrow(() -> new LocalWebServer());
            server.stop();
            assertTrue((output.toString().contains("Listening on port 8080")));
        }
    }

    // TODO : Basics GET requests then requesting files
}
