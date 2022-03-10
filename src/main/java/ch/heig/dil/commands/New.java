
package ch.heig.dil.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "new", description = "Init a new static website.")
public class New implements Runnable {
    @Override
    public void run() {
        System.out.println("New command");
    }
}
