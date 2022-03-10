
package ch.heig.dil.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "build", description = "Build the static website.")
public class Build implements Runnable {
    @Override
    public void run() {
        System.out.println("Build command");
    }
}
