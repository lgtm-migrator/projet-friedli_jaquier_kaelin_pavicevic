package ch.heig.dil.parsers;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.nio.file.Path;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Classe permettant de transformer du text markdown en html
 *
 * @author Jonathan Friedli
 * @author Valentin Kaelin
 */
public class MarkdownParser implements Helper<String> {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    /**
     * Initialise le template engine pour les fichiers markdown
     *
     * @param source : fichier source
     * @return le template engine
     * @throws IOException en cas d'erreur lors de la compilation du layout
     */
    public static Template getMarkdownTemplateEngine(Path source) throws IOException {
        TemplateLoader loader = new FileTemplateLoader(source.resolve("template").toFile());
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("md", new MarkdownParser());
        return handlebars.compile("layout");
    }

    /**
     * Transforme une string de markdown à html
     *
     * @param markdown : la string markdown à transformer
     * @param options : les options de la transformation, non utilisées ici
     * @return l'html sous forme de string
     */
    @Override
    public Object apply(String markdown, Options options) {
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
