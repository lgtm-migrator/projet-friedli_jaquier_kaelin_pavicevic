package ch.heig.dil.commands;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "serve", description = "Serve the static website.")
public class Serve implements Callable<Integer> {
    
    @Override
    public Integer call() throws IOException {
        System.out.println("Serve command");
        return 0;
    }
}
