
package ch.heig.dil.commands;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "clean", description = "Clean the generated content.")
public class Clean implements Callable<Integer> {
    
    @Override
    public Integer call() throws IOException {
        System.out.println("Clean command");
        return 0;
    }
}
