package ch.heig.dil.commands;

import ch.heig.dil.files.FilesHelper;
import java.io.IOException;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "init", description = "Init a new static website.")
public class Init implements Callable<Integer> {
    private static final String TEMPLATES_PATH = "templates/init";

    @CommandLine.Parameters(description = "Path of the static website")
    String path;

    @Override
    public Integer call() throws IOException {
        FilesHelper.copyDirectory(TEMPLATES_PATH, path);
        System.out.println("Website initialized.");
        return 0;
    }
}
