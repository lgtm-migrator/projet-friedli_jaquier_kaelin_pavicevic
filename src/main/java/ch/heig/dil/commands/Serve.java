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

    @Override
    public Integer call() throws IllegalArgumentException, JavalinException {
        Path path = Paths.get("public");

        // TODO : A changer quand build est implémenté
        if (!Files.isDirectory(path)) {
            System.err.println("The directory doesn't exist.");
            return CommandLine.ExitCode.USAGE;
        }

        // TODO: port en tant que args
        LocalWebServer server = new LocalWebServer(8080, path.toString());

        server.start();

        Scanner in = new Scanner(System.in);
        while (!in.hasNext("exit")) {
            in.nextLine();
        }

        server.stop();

        return CommandLine.ExitCode.OK;
    }
}
