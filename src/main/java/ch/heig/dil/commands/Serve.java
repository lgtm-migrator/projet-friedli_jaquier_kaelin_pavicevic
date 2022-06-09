package ch.heig.dil.commands;

import ch.heig.dil.servers.LocalWebServer;
import ch.heig.dil.watchers.DirectoryWatcher;
import ch.heig.dil.watchers.WatcherHandler;
import io.javalin.core.util.JavalinException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import picocli.CommandLine;

@CommandLine.Command(name = "serve", description = "Serve the static website.")
public class Serve implements Callable<Integer> {

    @CommandLine.Parameters(
            paramLabel = "PATH",
            description = "The path of the build site to serve")
    private Path path;

    @CommandLine.Option(
            names = {"-p", "--port"},
            paramLabel = "PORT",
            arity = "0..1",
            description = "The port on " + "which the server is listening",
            defaultValue = "8080")
    private int port;

    @CommandLine.Option(
            names = {"-w", "--watch"},
            paramLabel = "WATCH",
            arity = "0..1",
            description = "Watch for the file changes")
    private boolean watch = false;

    @Override
    public Integer call() throws IllegalArgumentException, JavalinException, IOException {
        new CommandLine(new Build()).execute(path.toString());

        Path out = path.resolve("build");
        if (!Files.isDirectory(out)) {
            System.err.println("The directory doesn't exist.");
            return CommandLine.ExitCode.USAGE;
        }

        LocalWebServer server;
        try {
            server = new LocalWebServer(port, out.toString());
        } catch (Exception e) {
            return -1;
        }

        if (watch) {
            WatcherHandler handler =
                    new WatcherHandler() {
                        @Override
                        public void handle(WatchEvent<?> event) {
                            System.out.println("File changed, rebuilding...");
                            new CommandLine(new Build()).execute(path.toString());
                            System.out.println("Static website rebuilt.");
                        }
                    };
            DirectoryWatcher watcher = new DirectoryWatcher(path, handler, true, false);
            watcher.addIgnoredFile(out);
            CompletableFuture.runAsync(watcher::processEvents);
        }

        server.start();

        Scanner in = new Scanner(System.in);
        while (!in.hasNext("exit")) {
            in.nextLine();
        }

        server.stop();

        return CommandLine.ExitCode.OK;
    }
}
