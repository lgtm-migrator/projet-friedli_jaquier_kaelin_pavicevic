package ch.heig.dil;

import static java.lang.Thread.sleep;

import ch.heig.dil.commands.Init;
import ch.heig.dil.files.FilesHelper;
import ch.heig.dil.watchers.DirectoryWatcher;
import ch.heig.dil.watchers.WatcherHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class DirectoryWatcherTest {
    private static final String FOLDER = "./test-watcher";
    private static final String IGNORED = FOLDER + "/build";

    /** Crée le dossier de test avant chaque test */
    @BeforeEach
    protected void setup() {
        try {
            FilesHelper.deleteDirectory(FOLDER);
        } catch (Exception ignored) {
        }
        String[] args = new String[] {FOLDER};
        CommandLine cmd = new CommandLine(new Init());
        cmd.execute(args);
    }

    /** Supprime le dossier de test après chaque test */
    @AfterEach
    protected void clean() {
        //        FilesHelper.deleteDirectory(FOLDER);
    }

    @Test
    void testWatcherShouldAlertOnNewFile() throws IOException, InterruptedException {
        List<WatchEvent<?>> events = new LinkedList<>();
        WatcherHandler handler =
                new WatcherHandler() {
                    @Override
                    public void handle(WatchEvent<?> event) {
                        System.out.println("File created");
                        events.add(event);
                    }
                };
        DirectoryWatcher watcher = new DirectoryWatcher(Paths.get(FOLDER), handler, true, false);
        CompletableFuture.runAsync(watcher::processEvents);

        // Create file
        Files.createFile(Paths.get(FOLDER + "/test2.md"));
        sleep(5000);
        watcher.stop();

        System.out.println(events.size());

        assert (events.size() == 1); // TODO: not working
    }

    // Test: new folder

    // Test: edit file

    // Test : delete file

    // Test: ignored file
    // watcher.addIgnoredFile(out);
}
