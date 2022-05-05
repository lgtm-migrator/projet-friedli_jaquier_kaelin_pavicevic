package ch.heig.dil;

import ch.heig.dil.commands.*;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        mixinStandardHelpOptions = true,
        versionProvider = Boomshot.VersionProvide.class,
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

    static class VersionProvide implements CommandLine.IVersionProvider {

        public String[] getVersion() throws Exception {
            URL url = getClass().getResource("/version.properties");
            if (url == null) {
                return new String[]{"No version.properties file found in the classpath. Is examples.jar in the classpath?"};
            }
            Properties properties = new Properties();
            properties.load(url.openStream());
            return new String[]{
                    properties.getProperty("application") + " " + properties.getProperty("version")
            };
        }
    }
}
