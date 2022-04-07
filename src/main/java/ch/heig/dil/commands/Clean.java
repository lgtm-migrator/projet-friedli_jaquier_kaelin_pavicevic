package ch.heig.dil.commands;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "clean", description = "Clean the generated content.")
public class Clean implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "Path to site to clean")
    String pathToSite;

    @Override
    public Integer call() throws IOException {
        File site = new File(System.getProperty("user.dir") + pathToSite + "/build");
        if (!site.delete()) {
            throw new IOException("Clean command failed");
        }
        System.out.println("The site has been cleaned");
        return 0;
    }
}
