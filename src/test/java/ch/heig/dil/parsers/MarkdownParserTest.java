package ch.heig.dil.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.commonmark.node.*;
import org.junit.jupiter.api.Test;

public class MarkdownParserTest {

    private final MarkdownParser parser = new MarkdownParser();

    @Test
    void translateSimpleParagraph() {
        assertEquals("<p>This is <em>Sparta</em></p>\n", parser.apply(("This is *Sparta*"), null));
    }

    @Test
    void translateFailedBoldText() {
        assertEquals("<p>I am *bold</p>\n", parser.apply("I am *bold", null));
    }

    @Test
    void translateFailedBacktickText() {
        assertEquals("<p>I am ```code``</p>\n", parser.apply("I am ```code``", null));
    }

    @Test
    void translateTitle1() {
        assertEquals("<h1>Mon premier article</h1>\n", parser.apply("# Mon premier article", null));
    }

    @Test
    void translateTitle2() {
        assertEquals("<h2>Mon sous-titre</h2>\n", parser.apply("## Mon sous-titre", null));
    }
}
