package ch.heig.dil.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import org.codehaus.plexus.util.FileUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "init", description = "Init a new static website.")
public class Init implements Callable<Integer> {
    private static final String TEMPLATES_PATH = "templates/init";

    @CommandLine.Parameters(description = "Path of the static website")
    String path;

    @Override
    public Integer call() throws IOException {
        Path websitePath = Paths.get(path);
        Path directory = Files.createDirectories(websitePath);
        FileUtils.copyDirectory(new File(TEMPLATES_PATH), directory.toFile());
        System.out.println("Website initialized.");

        return 0;
    }
}
