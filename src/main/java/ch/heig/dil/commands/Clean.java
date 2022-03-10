
package ch.heig.dil.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "clean", description = "Clean the generated content.")
public class Clean implements Runnable {
    @Override
    public void run() {
        System.out.println("Clean command");
    }
}
