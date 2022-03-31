package ch.heig.dil.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.commonmark.node.*;
import org.junit.jupiter.api.Test;

public class MarkdownParserTest {

    @Test
    void translateSimpleParagraph() {
        assertEquals(
                "<p>This is <em>Sparta</em></p>\n",
                MarkdownParser.markdownToHtml("This is *Sparta*"));
    }

    @Test
    void translateFailedBoldText() {
        assertEquals("<p>I am *bold</p>\n", MarkdownParser.markdownToHtml("I am *bold"));
    }

    @Test
    void translateFailedBacktickText() {
        assertEquals("<p>I am ```code``</p>\n", MarkdownParser.markdownToHtml("I am ```code``"));
    }

    @Test
    void translateTitle1() {
        assertEquals(
                "<h1>Mon premier article</h1>\n",
                MarkdownParser.markdownToHtml("# Mon premier article"));
    }

    @Test
    void translateTitle2() {
        assertEquals(
                "<h2>Mon sous-titre</h2>\n", MarkdownParser.markdownToHtml("## Mon sous-titre"));
    }

    @Test
    void translateImage() {
        assertEquals(
                "<img src=\"./image.png\" alt=\"Une image\"/>\n",
                MarkdownParser.markdownToHtmlImage("![Une image](./image.png)"));
    }
}
