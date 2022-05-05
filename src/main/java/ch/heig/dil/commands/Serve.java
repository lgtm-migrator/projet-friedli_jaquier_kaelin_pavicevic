package ch.heig.dil.commands;

import ch.heig.dil.servers.LocalWebServer;
import io.javalin.core.util.JavalinException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Callable;
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

    @Override
    public Integer call() throws IllegalArgumentException, JavalinException {
        new CommandLine(new Build()).execute(path.toString());

        path = Paths.get(path.toString(), "/build");
        if (!Files.isDirectory(path)) {
            System.err.println("The directory doesn't exist.");
            return CommandLine.ExitCode.USAGE;
        }

        LocalWebServer server = new LocalWebServer(port, path.toString());

        server.start();

        Scanner in = new Scanner(System.in);
        while (!in.hasNext("exit")) {
            in.nextLine();
        }

        server.stop();

        return CommandLine.ExitCode.OK;
    }
}
