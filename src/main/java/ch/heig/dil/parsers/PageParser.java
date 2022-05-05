package ch.heig.dil.parsers;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Parse une page markdown contenant du YAML et de l'HTML en injectant les variables de
 * configuration
 *
 * @author Valentin Kaelin
 */
public class PageParser {

    /**
     * Parse un fichier html contenant du yaml et du markdown
     *
     * @param source : fichier markdown
     * @param config : configuration globale
     * @param template : template engine pour le markdown
     * @return l'html sous forme de string
     * @throws IOException si le fichier n'est pas valide
     */
    public static String parse(Path source, Map<String, String> config, Template template)
            throws IOException {
        // Split les metadata et le contenu (---)
        String[] array = Files.readString(source).split("---");
        if (array.length != 2) {
            throw new RuntimeException("The page is malformed");
        }

        Map<String, String> page = ParserYAML.getMetaDataFromString(array[0]);
        String content = array[1];

        // Injection des variables dans le template
        Context context =
                Context.newBuilder(new Object())
                        .combine("site", config)
                        .combine("page", page)
                        .combine("content", content)
                        .resolver(MapValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE)
                        .build();

        // Rendu de la page web
        return template.apply(context);
    }
}
