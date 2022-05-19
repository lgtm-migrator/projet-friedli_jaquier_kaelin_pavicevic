package ch.heig.dil.commands;

import ch.heig.dil.files.FilesHelper;
import ch.heig.dil.parsers.MarkdownParser;
import ch.heig.dil.parsers.PageParser;
import ch.heig.dil.parsers.ParserYAML;
import ch.heig.dil.watchers.DirectoryWatcher;
import com.github.jknack.handlebars.Template;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import picocli.CommandLine;

/**
 * Build le site statique en transformant les fichiers markdown en html
 *
 * @author Valentin Kaelin
 */
@CommandLine.Command(name = "build", description = "Build the static website.")
public class Build implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Source of the static website")
    private Path sourcePath;

    @CommandLine.Option(
            names = {"-w", "--watch"},
            paramLabel = "WATCH",
            arity = "0..1",
            description = "Watch for the file changes")
    private boolean watch = false;

    @Override
    public Integer call() throws IOException, InterruptedException {
        Path out = sourcePath.resolve("build");
        buildFiles(out);
        System.out.println("Static website built.");

        if (watch) {
            // TODO: watch for changes
            System.out.println("WATCH MODE ON");

            WatchService watchService = FileSystems.getDefault().newWatchService();
            sourcePath.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            CountDownLatch countDownLatch = new CountDownLatch(10);

            DirectoryWatcher watcher = new DirectoryWatcher(watchService, countDownLatch);

            new Thread(watcher).start();
            countDownLatch.await(10, TimeUnit.SECONDS);
        }

        return CommandLine.ExitCode.OK;
    }

    private void buildFiles(Path out) throws IOException {
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
                                Path destination = out.resolve(sourcePath.relativize(source));
                                try {
                                    Files.createDirectories(destination.getParent());
                                    if (source.toString().endsWith(".md")) {
                                        String html = PageParser.parse(source, config, template);

                                        // Sauvegarde du fichier html
                                        Path target =
                                                sourcePath
                                                        .resolve("build")
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
    }
}
