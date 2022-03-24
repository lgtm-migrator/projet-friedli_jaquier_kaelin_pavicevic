package ch.heig.dil.parsers;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParser {
    public static String markdownToHtml(String line){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(line);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    public static String markdownToHtmlImage(String line){
        String temp = markdownToHtml(line);
        temp = temp.substring(3);
        temp = temp.substring(0, temp.length()-5);
        StringBuilder sb = new StringBuilder(temp);
        sb.delete(temp.length() - 3, temp.length() - 2).append("\n");
        return sb.toString();
    }
}
