
package ch.heig.dil.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "serve", description = "Run the serve command")
public class Serve implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Serve command");
        return 0;
    }
}
