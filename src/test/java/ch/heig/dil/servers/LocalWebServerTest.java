package ch.heig.dil.servers;

import static org.junit.jupiter.api.Assertions.*;

import io.javalin.core.util.JavalinException;
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
        var server = assertDoesNotThrow(() -> new LocalWebServer());
    }

    @Test
    void ConstructorThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> new LocalWebServer(-1000, "public"));
    }

    @Test
    void ConstructorThrowsJavalinException() {
        assertThrows(JavalinException.class, () -> new LocalWebServer(5000, "fakeDirectory"));
    }

    @Test
    void WebServerIsListening() throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            var server = assertDoesNotThrow(() -> new LocalWebServer());
            server.start();
            assertTrue((output.toString().contains("Listening on http://localhost:8080")));
            server.stop();
        }
    }

    @Test
    void ServerGivesStatusCode200ForIndex() {
        int port = 8080;
        var server = assertDoesNotThrow(() -> new LocalWebServer(port));
        server.start();
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

    @Test
    void ServerGivesStatusCode200ForNestedPage() {

        int port = 8080;
        var server = assertDoesNotThrow(() -> new LocalWebServer(port));
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/nested/page.html"))
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

    @Test
    void ServerGivesStatusCode404() {

        int port = 8080;
        var server = assertDoesNotThrow(() -> new LocalWebServer(port));
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/fakePage"))
                        .header("Content-Type", "text/html")
                        .GET()
                        .build();

        HttpResponse<String> response =
                assertDoesNotThrow(
                        () -> client.send(request, HttpResponse.BodyHandlers.ofString()));
        server.stop();
        assertEquals(response.statusCode(), 404);
    }
}
