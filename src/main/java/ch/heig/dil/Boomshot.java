package ch.heig.dil;

import ch.heig.dil.commands.*;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * Classe principale du projet Boomshot
 *
 * @author Jonathan Friedli
 */
@CommandLine.Command(
        mixinStandardHelpOptions = true,
        versionProvider = Boomshot.VersionProvide.class,
        description = "A static site generator",
        subcommands = {Init.class, Clean.class, Build.class, Serve.class, Publish.class})
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

    /** Classe interne permettant de récuper la version du projet dans le pom.xml */
    static class VersionProvide implements CommandLine.IVersionProvider {

        /**
         * Renvoie la version du projet qui se trouve dans le fichier pom.xml. Nous avons un fichier
         * version.properties qui fait le lien entre le pom.xml et le versionProvide
         *
         * @return La version
         * @throws Exception Si on ne trouve pas le fichier version.properties
         */
        public String[] getVersion() throws Exception {
            URL url = getClass().getResource("/version.properties");
            if (url == null) {
                return new String[] {"No version.properties file found in the classpath !"};
            }
            Properties properties = new Properties();
            properties.load(url.openStream());
            return new String[] {
                properties.getProperty("application") + " " + properties.getProperty("version")
            };
        }
    }
}
