package ch.heig.dil;
import picocli.CommandLine;
import ch.heig.dil.commands.*;

@CommandLine.Command(
        mixinStandardHelpOptions = true,
        name = "static",
        version = "0.1",
        description = "A static site generator",
        subcommands = {
                New.class,
                Clean.class,
                Build.class,
                Serve.class
        }
)
public class Application implements Runnable {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }
    
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}