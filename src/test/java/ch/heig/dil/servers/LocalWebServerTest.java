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
        server.stop();
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
            assertTrue((output.toString().contains("Listening on port 8080")));
            server.stop();
        }
    }

    @Test
    void ServerGivesStatusCode200ForIndex() {

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

    @Test
    void ServerGivesStatusCode200ForNestedPage() {

        var server = assertDoesNotThrow(() -> new LocalWebServer());
        int port = 8080;
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

        var server = assertDoesNotThrow(() -> new LocalWebServer());
        int port = 8080;
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
