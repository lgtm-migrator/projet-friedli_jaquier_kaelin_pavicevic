package ch.heig.dil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Serveur web local permettant d'afficher le contenu du site statique
 *
 * @author Lazar Pavicevic
 */
public class LocalWebServer {
    private final HttpServer server;
    private final int port;

    /**
     * Constructeur par défaut bindant le serveur au port 8080.
     *
     * @throws IOException
     */
    public LocalWebServer() throws IOException {
        this(8080);
    }

    /**
     * Constructeur permettant de choisir le port à bind.
     *
     * @param port Port à utiliser
     * @throws IOException
     */
    public LocalWebServer(int port) throws IOException {
        this.port = port;
        InetSocketAddress sockAddr = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        server = HttpServer.create(sockAddr, 0);
        run();
    }

    /** Arrête le serveur en attendant les requêtes en attente s'il y en a. */
    public void stop() {
        server.stop(0);
    }

    /**
     * Lance le serveur en créant des mapping entre les URIs possibles et les handlers qui vont
     * traiter les requêtes
     */
    private void run() {
        // TODO : Bind only on HTMLHandler
        server.createContext("/", new HelloHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Listening on port " + port);
    }

    /** Handler de test renvoyant une réponse GET 200 avec le contenu de l'URI utilisée */
    private static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String response = "Hello from request: " + requestURI;
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    /** Handler renvoyant une réponse GET avec le contenu statique du site */
    private static class HTMLHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                // TODO : Read html content and stream as response
            }
        }
    }
}
