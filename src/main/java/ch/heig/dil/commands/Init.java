package ch.heig.dil.commands;

import ch.heig.dil.files.FilesHelper;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * Initialise le dossier d'un nouveau site avec un contenu par défaut
 *
 * @author Valentin Kaelin
 */
@CommandLine.Command(name = "init", description = "Init a new static website.")
public class Init implements Callable<Integer> {
    private static final String TEMPLATES_PATH = "/templates/init";

    @CommandLine.Parameters(description = "Path of the static website")
    Path path;

    @Override
    public Integer call() {
        try {
            // Path templateRessource = FilesHelper.getRessourcePath(TEMPLATES_PATH);

            URI uri = this.getClass().getResource(TEMPLATES_PATH).toURI();

            // Initialize a zip file system when the template is stored in a jar file
            if (uri.getScheme().equals("jar")) {
                Map<String, String> env = new HashMap<>();
                env.put("create", "true");
                FileSystems.newFileSystem(uri, env);
            }

            Path template = Paths.get(uri);
            FilesHelper.copyDirectory(template, path);
            System.out.println("Website initialized.");
        } catch (Exception ex) {
            return 1;
        }
        return 0;
    }
}
