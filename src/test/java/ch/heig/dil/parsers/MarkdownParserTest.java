package ch.heig.dil.parsers;

import ch.heig.dil.Boomshot;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownParserTest {

    @Test
    void paragraph() {
        assertEquals("<p>This is <em>Sparta</em></p>\n", MarkdownParser.markdownToHtml("This is *Sparta*"));
    }

    @Test
    void title1(){
        assertEquals("<h1>Mon premier article</h1>\n", MarkdownParser.markdownToHtml("# Mon premier article"));
    }

    @Test
    void title2(){
        assertEquals("<h2>Mon sous-titre</h2>\n", MarkdownParser.markdownToHtml("## Mon sous-titre"));
    }

    @Test
    void image(){
        assertEquals("<img src=\"./image.png\" alt=\"Une image\"/>\n", MarkdownParser.markdownToHtmlImage("![Une image](./image.png)"));
    }
}
