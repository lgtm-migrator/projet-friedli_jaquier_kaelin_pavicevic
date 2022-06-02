package ch.heig.dil.watchers;

import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;

import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

/**
 * Observe récursivement des modifications sur un répertoire.
 * Code initial: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java
 *
 * @author Valentin Kaelin
 */
public class DirectoryWatcher {
    private final WatchService watcher;
    private final WatcherHandler handler;
    private final Map<WatchKey, Path> keys;
    private final List<Path> ignoredFiles;

    private final boolean recursive;
    private final boolean debug;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    /**
     * Enregistre le répertoire donné avec le WatchService
     *
     * @param dir : répertoire à observer
     * @throws IOException en cas d'erreur d'IO
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (debug) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Enregistre le répertoire donné et ses sous-dossiers avec le WatchService
     *
     * @param start : répertoire racine à observer
     * @throws IOException
     */
    private void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Crée le WatchService et enregistre récursivement le répertoire donné
     *
     * @param dir       : répertoire racine à observer
     * @param handler   : gestionnaire d'événements
     * @param recursive : observe récursivement les sous-dossiers ou non
     * @param debug     : affiche les messages de debug ou non
     * @throws IOException en cas d'erreur IO
     */
    public DirectoryWatcher(Path dir, WatcherHandler handler,
                            boolean recursive, boolean debug) throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        this.handler = handler;
        keys = new HashMap<>();
        ignoredFiles = new ArrayList<>();
        this.recursive = recursive;

        if (recursive)
            registerAll(dir);
        else
            register(dir);

        this.debug = debug;
    }

    /**
     * Traite tous les événements mis en attente dans le watcher.
     */
    public void processEvents() {
        System.out.println("Watching for changes...");
        while (true) {
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!");
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);

                // Check if file is ignored
                boolean isFileIgnored = false;
                for (Path ignoredFile : ignoredFiles) {
                    if (child.startsWith(ignoredFile)) {
                        isFileIgnored = true;
                        break;
                    }
                }

                if (isFileIgnored) {
                    continue;
                }

                // print out event
                if (debug) {
                    System.out.format("%s: %s\n", event.kind().name(), child);
                }

                // handle event
                try {
                    handler.handle(event);
                } catch (IOException ignored) {
                }

                // if directory is created, and watching recursively, then
                // register it and its subdirectories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException ignored) {
                    }
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

    /**
     * Ajoute le chemin aux fichiers ignorés
     *
     * @param file : path à ignorer
     */
    public void addIgnoredFile(Path file) {
        ignoredFiles.add(file);
    }

    /**
     * Stoppe l'observation
     *
     * @throws IOException en cas d'erreur IO
     */
    public void stop() throws IOException {
        watcher.close();
    }
}
