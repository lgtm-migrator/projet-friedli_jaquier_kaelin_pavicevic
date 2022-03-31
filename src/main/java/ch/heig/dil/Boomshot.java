package ch.heig.dil;

import ch.heig.dil.commands.*;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        mixinStandardHelpOptions = true,
        name = "boomshot",
        version = "0.1",
        description = "A static site generator",
        subcommands = {New.class, Clean.class, Build.class, Serve.class})
public class Boomshot implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Boomshot()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        CommandLine.usage(this, System.out);
        return 0;
    }
}
