package ch.heig.dil.parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class PageParserTest {

    @Test
    void canParsePage() {
        String toParse =
                "titre: Mon premier article\n"
                        + "auteur: Bertil Chapuis\n"
                        + "date: 2021-03-10\n"
                        + "---\n"
                        + "# Mon premier article\n"
                        + "## Mon sous-titre\n"
                        + "Le contenu de mon article.\n\n"
                        + "![Une image](./image.png)";

        String toCompare =
                "<h1>Mon premier article</h1>\n"
                        + "<h2>Mon sous-titre</h2>\n"
                        + "<p>Le contenu de mon article.</p>\n"
                        + "<p><img src=\"./image.png\" alt=\"Une image\" /></p>\n";
        String result = assertDoesNotThrow(() -> PageParser.parse(toParse));
        assertEquals(toCompare, result);
    }

    @Test
    void PageParserThrowIOException() {
        String toParse = "titre: Title\n" + "# Test Title";
        assertThrows(IOException.class, () -> PageParser.parse(toParse));
    }
}
