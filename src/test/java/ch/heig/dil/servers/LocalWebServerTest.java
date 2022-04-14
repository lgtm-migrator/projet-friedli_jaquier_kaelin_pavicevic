package ch.heig.dil.servers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    @Test
    void WebServerGivesStatusCode200ForIndex() {

        var server = assertDoesNotThrow(() -> new LocalWebServer());
        int port = 8080;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/"))
                        .header("Content-Type", "text/html")
                        .GET()
                        .build();

        HttpResponse<String> response =
                assertDoesNotThrow(
                        () -> client.send(request, HttpResponse.BodyHandlers.ofString()));

        System.out.println(response.body());
        server.stop();
        assertEquals(response.statusCode(), 200);
    }
}
