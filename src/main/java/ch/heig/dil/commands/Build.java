package ch.heig.dil.commands;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "build", description = "Build the static website.")
public class Build implements Callable<Integer> {
    
    @Override
    public Integer call() throws IOException {
        System.out.println("Build command");
        return 0;
    }
}
