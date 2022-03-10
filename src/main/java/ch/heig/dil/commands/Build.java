
package ch.heig.dil.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "build", description = "Run the build command")
public class Build implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Build command");
        return 0;
    }
}
