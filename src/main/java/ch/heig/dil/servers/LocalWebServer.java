package ch.heig.dil.servers;

import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;
import io.javalin.http.staticfiles.Location;
import java.io.IOException;

/**
 * Serveur web local permettant d'afficher le contenu du site statique
 *
 * @author Lazar Pavicevic
 */
public class LocalWebServer {
    private final Javalin server;

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
     * @throws RuntimeException levée quand le port est invalide
     */
    public LocalWebServer(int port) {
        if (port < 1) throw new RuntimeException("Error : invalid port");
        System.out.println("Listening on port " + port);
        server =
                Javalin.create(
                                config -> {
                                    org.eclipse.jetty.util.log.Log.getProperties()
                                            .setProperty("org.eclipse.jetty.LEVEL", "OFF");
                                    org.eclipse.jetty.util.log.Log.getProperties()
                                            .setProperty(
                                                    "org.eclipse.jetty.util.log.announce", "false");
                                    JavalinLogger.startupInfo = false;
                                    JavalinLogger.enabled = false;
                                    config.addStaticFiles(
                                            staticFileConfig -> {
                                                staticFileConfig.hostedPath = "/";
                                                staticFileConfig.directory = "testing"; // A changer
                                                staticFileConfig.location = Location.EXTERNAL;
                                            });
                                })
                        .start(port);
    }

    public void stop() {
        server.stop();
    }
}
