package ch.heig.dil.commands;

import ch.heig.dil.files.FilesHelper;
import ch.heig.dil.parsers.PageParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

    @CommandLine.Parameters(index = "0", description = "Source of the static website")
    private String sourcePath;

    public static final List<String> excluded = List.of("config.yml", "build");

    @Override
    public Integer call() throws IOException {
        String BUILD_DIRECTORY = "build";
        FilesHelper.cleanDirectoy(BUILD_DIRECTORY);
        Path out = Paths.get(BUILD_DIRECTORY);
        Path sourceOfPath = Paths.get(sourcePath);

        if (!sourceOfPath.toFile().exists()) {
            System.err.println("Invalid init folder " + sourcePath);
            return CommandLine.ExitCode.SOFTWARE;
        }

        try (Stream<Path> walk = Files.walk(sourceOfPath)) {
            walk.filter(Files::isRegularFile)
                    .filter(p -> !excluded.contains(p.getFileName().toString()))
                    .forEach(
                            source -> {
                                Path destination = out.resolve(sourceOfPath.relativize(source));
                                try {
                                    Files.createDirectories(destination.getParent());
                                    if (source.getFileName().toString().endsWith(".md")) {
                                        destination =
                                                Path.of(
                                                        destination
                                                                .toString()
                                                                .replace(".md", ".html"));
                                        String markdown =
                                                PageParser.parse(Files.readString(source));

                                        Files.writeString(destination, markdown);
                                    } else {
                                        Files.copy(source, destination);
                                    }
                                } catch (IOException e) {
                                    //                                    e.printStackTrace();
                                }
                            });
        }
        System.out.println("Static website built.");

        return CommandLine.ExitCode.OK;
    }
}
