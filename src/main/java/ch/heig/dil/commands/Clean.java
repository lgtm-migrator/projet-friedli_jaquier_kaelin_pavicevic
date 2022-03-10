
package ch.heig.dil.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "clean", description = "Run the clean command")
public class Clean implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Clean command");
        return 0;
    }
}
