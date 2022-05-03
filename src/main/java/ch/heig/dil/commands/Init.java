package ch.heig.dil.commands;

import ch.heig.dil.files.FilesHelper;
import java.nio.file.Path;
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
    String path;

    @Override
    public Integer call() {
        try {
            Path templateRessource = FilesHelper.getRessourcePath(TEMPLATES_PATH);
            FilesHelper.copyDirectory(templateRessource.toString(), path);
            System.out.println("Website initialized.");
        } catch (Exception ex) {
            return 1;
        }
        return 0;
    }
}
