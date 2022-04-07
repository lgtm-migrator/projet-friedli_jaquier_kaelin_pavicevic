package ch.heig.dil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

public class LocalWebServer {
    private final HttpServer server;
    private final int port;

    public LocalWebServer() throws IOException {
        this(8080);
    }

    public LocalWebServer(int port) throws IOException {
        this.port = port;
        InetSocketAddress sockAddr = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        server = HttpServer.create(sockAddr, 0);
        run();
    }

    public void stop() {
        server.stop(0);
    }

    private void run() {

        // TODO : Bind only on HTMLHandler
        server.createContext("/", new HelloHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Listening on port " + port);
    }

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
