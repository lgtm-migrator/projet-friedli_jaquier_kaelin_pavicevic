package ch.heig.dil.watchers;

import java.io.IOException;
import java.nio.file.WatchEvent;

/**
 * Traite les événements de modification sur les fichiers d'un répertoire.
 *
 * @author Valentin Kaelin
 */
public abstract class WatcherHandler {
    /**
     * Traite un événement de modification sur un fichier.
     *
     * @param event : événement de modification
     * @throws IOException en cas d'erreur d'IO
     */
    public abstract void handle(WatchEvent<?> event) throws IOException;
}
