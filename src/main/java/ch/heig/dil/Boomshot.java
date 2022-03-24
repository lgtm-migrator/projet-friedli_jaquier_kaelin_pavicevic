package ch.heig.dil;

import ch.heig.dil.commands.*;
import picocli.CommandLine;

@CommandLine.Command(
        mixinStandardHelpOptions = true,
        name = "boomshot",
        version = "0.1",
        description = "A static site generator",
        subcommands = {New.class, Clean.class, Build.class, Serve.class})
public class Boomshot implements Runnable {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Boomshot()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
