package ch.heig.dil.parsers;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Classe permettant de transformer du text markdown en html
 *
 * @author Jonathan Friedli
 */
public class MarkdownParser {

    /**
     * Transformer une string de markdown à html
     *
     * @param line Chaine de caractère à transformer
     * @return Chaine de caractère au format html
     */
    public static String markdownToHtml(String line) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(line);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * Prend une string contenant une image au format markdown et la transforme en balise html
     * contentant ladite image.
     *
     * @param line String en markdown
     * @return La balise, contenant l'image, au format html
     */
    public static String markdownToHtmlImage(String line) {
        String temp = markdownToHtml(line);
        temp = temp.substring(3);
        temp = temp.substring(0, temp.length() - 5);
        StringBuilder sb = new StringBuilder(temp);
        sb.delete(temp.length() - 3, temp.length() - 2).append("\n");
        return sb.toString();
    }
}
