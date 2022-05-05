package ch.heig.dil.servers;

import static org.junit.jupiter.api.Assertions.*;

import ch.heig.dil.commands.Build;
import ch.heig.dil.commands.Init;
import ch.heig.dil.files.FilesHelper;
import io.javalin.core.util.JavalinException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class LocalWebServerTest {
    private static final String INIT_FOLDER = "./test-build";
    private static final String BUILD_FOLDER = INIT_FOLDER + "/build";

    /** Crée le dossier de test avant chaque test */
    @BeforeEach
    protected void setup() {
        try {
            FilesHelper.deleteDirectory(INIT_FOLDER);
        } catch (Exception ignored) {
        }
        String[] args = new String[] {INIT_FOLDER};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);
        cmd = new CommandLine(new Build());
        cmd.execute(args);
    }

    /**
     * Supprime le dossier de test après chaque test
     *
     * @throws IOException si une erreur apparait lors de la suppression du dossier
     */
    @AfterEach
    protected void clean() throws IOException {
        FilesHelper.deleteDirectory(INIT_FOLDER);
    }

    @Test
    void ConstructorDoesntThrow() {
        var server = assertDoesNotThrow(() -> new LocalWebServer(BUILD_FOLDER));
    }

    @Test
    void ConstructorThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> new LocalWebServer(-1000, BUILD_FOLDER));
    }

    @Test
    void ConstructorThrowsJavalinException() {
        assertThrows(JavalinException.class, () -> new LocalWebServer(5000, "fakeDirectory"));
    }

    @Test
    void WebServerIsListening() throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            var server = assertDoesNotThrow(() -> new LocalWebServer(BUILD_FOLDER));
            server.start();
            assertTrue((output.toString().contains("Listening on http://localhost:8080")));
            server.stop();
        }
    }

    @Test
    void ServerGivesStatusCode200ForIndex() {
        int port = 8080;
        var server = assertDoesNotThrow(() -> new LocalWebServer(port, BUILD_FOLDER));
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
        var server = assertDoesNotThrow(() -> new LocalWebServer(port, BUILD_FOLDER));
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/pages/page.html"))
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
        var server = assertDoesNotThrow(() -> new LocalWebServer(port, BUILD_FOLDER));
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
