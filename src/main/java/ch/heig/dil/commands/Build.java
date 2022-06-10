package ch.heig.dil.commands;

import ch.heig.dil.files.FilesHelper;
import ch.heig.dil.parsers.MarkdownParser;
import ch.heig.dil.parsers.PageParser;
import ch.heig.dil.parsers.ParserYAML;
import com.github.jknack.handlebars.Template;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import picocli.CommandLine;

/**
 * Build le site statique en transformant les fichiers markdown en html
 *
 * @author Valentin Kaelin
 */
@CommandLine.Command(name = "build", description = "Build the static website.")
public class Build implements Callable<Integer> {

    public static final String BUILD_FOLDER = "build";

    @CommandLine.Parameters(index = "0", description = "Source of the static website")
    private Path sourcePath;

    @Override
    public Integer call() throws IOException {
        Path out = sourcePath.resolve(BUILD_FOLDER);

        // Parse la configuration
        Map<String, String> config = ParserYAML.parseConfig(sourcePath);

        // Initialise le template engine
        Template template = MarkdownParser.getMarkdownTemplateEngine(sourcePath);

        // Suppression du dossier build s'il existe
        FilesHelper.deleteDirectory(out.toString());

        try (Stream<Path> walk = Files.walk(sourcePath)) {
            walk.filter(Files::isRegularFile)
                    .filter(p -> !p.toString().endsWith(".hbs"))
                    .filter(p -> !p.toString().contains(ParserYAML.CONFIG_FILE))
                    .forEach(
                            source -> {
                                System.out.println("--> " + source);
                                Path destination = out.resolve(sourcePath.relativize(source));
                                try {
                                    Files.createDirectories(destination.getParent());
                                    if (source.toString().endsWith(".md")) {
                                        String html = PageParser.parse(source, config, template);

                                        // Sauvegarde du fichier html
                                        Path target =
                                                sourcePath
                                                        .resolve(BUILD_FOLDER)
                                                        .resolve(
                                                                sourcePath
                                                                        .relativize(source)
                                                                        .toString()
                                                                        .replace(".md", ".html"));
                                        Files.writeString(
                                                target,
                                                html,
                                                StandardOpenOption.CREATE,
                                                StandardOpenOption.TRUNCATE_EXISTING);
                                    } else {
                                        Files.copy(source, destination);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
        }
        System.out.println("Static website built.");

        return CommandLine.ExitCode.OK;
    }
}
