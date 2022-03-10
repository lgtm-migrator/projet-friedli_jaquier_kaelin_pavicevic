
package ch.heig.dil.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "new", description = "Run the new command")
public class New implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("New command");
        return 0;
    }
}
