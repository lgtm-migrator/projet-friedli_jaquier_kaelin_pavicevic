package ch.heig.dil.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "serve", description = "Serve the static website.")
public class Serve implements Runnable {
    @Override
    public void run() {
        System.out.println("Serve command");
    }
}
