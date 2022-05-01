package ch.heig.dil.servers;

import io.javalin.Javalin;
import io.javalin.core.util.JavalinException;
import io.javalin.core.util.JavalinLogger;
import io.javalin.http.staticfiles.Location;
import org.eclipse.jetty.util.log.Log;

/**
 * Serveur web local permettant d'afficher le contenu du site statique
 *
 * @author Lazar Pavicevic
 */
public class LocalWebServer {
    private final Javalin server;

    /** Constructeur par défaut utilisant le port 8080 et le répertoir "public". */
    public LocalWebServer() {
        this(8080);
    }

    /**
     * Constructeur permettant seuleument de choisir le port à bind et utilise le répertoir "public.
     *
     * @param port Port à utiliser
     */
    public LocalWebServer(int port) {
        this(port, "public");
    }

    /**
     * Constructeur permettant de choisir le port à bind et le chemin du répertoire contenant les
     * fichiers.
     *
     * @param port Port à utiliser
     * @param directory Dossier contenant les pages statiques à afficher
     * @throws IllegalArgumentException levée quand le port est invalide
     * @throws JavalinException levée quand le directory est invalide
     */
    public LocalWebServer(int port, String directory)
            throws IllegalArgumentException, JavalinException {
        if (port < 1) throw new IllegalArgumentException("Error : invalid port");
        System.out.println("Listening on port " + port);
        server =
                Javalin.create(
                                config -> {
                                    Log.getProperties()
                                            .setProperty("org.eclipse.jetty.LEVEL", "OFF");
                                    Log.getProperties()
                                            .setProperty(
                                                    "org.eclipse.jetty.util.log.announce", "false");
                                    JavalinLogger.startupInfo = false;
                                    JavalinLogger.enabled = false;
                                    config.addStaticFiles(
                                            staticFileConfig -> {
                                                staticFileConfig.directory = directory;
                                                staticFileConfig.location = Location.EXTERNAL;
                                            });
                                })
                        .start(port);
    }

    public void stop() {
        server.stop();
    }
}
