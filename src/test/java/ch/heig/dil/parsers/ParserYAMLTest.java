package ch.heig.dil.parsers;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ParserYAMLTest {
    
    @Test
    void canParseYAML() {
        Map<String,String> mapToCompare = new HashMap<>();
        mapToCompare.put("titre","Title");
        mapToCompare.put("auteur","Author");
        mapToCompare.put("date","2022-03-19");
        
        String toParse = "titre: Title\n" +
                "auteur: Author\n" +
                "date: 2022-03-19";
        Map<String,String> result = ParserYAML.Parse(toParse);
        assertEquals(mapToCompare,result);
    }
}
