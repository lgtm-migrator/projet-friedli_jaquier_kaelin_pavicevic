package ch.heig.dil.parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ParserYAMLTest {

    @Test
    void canParseYAML() {
        Map<String, String> mapToCompare = new HashMap<>();
        mapToCompare.put("titre", "Title");
        mapToCompare.put("auteur", "Author");
        mapToCompare.put("date", "2022-03-19");

        String toParse = "titre: Title\n" + "auteur: Author\n" + "date: 2022-03-19";

        Map<String, String> result =
                assertDoesNotThrow(() -> ParserYAML.getMetaDataFromString(toParse));
        assertEquals(mapToCompare, result);
    }

    @Test
    void ParseYAMLThrowsIOException() {
        String toParse = "titre: Title\n" + "Author\n" + "date: 2022-03-19";
        assertThrows(IOException.class, () -> ParserYAML.getMetaDataFromString(toParse));
    }
}
