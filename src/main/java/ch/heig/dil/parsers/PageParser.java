package ch.heig.dil.parsers;

import java.io.IOException;
import java.util.Map;

public class PageParser {
    public static String parse(String file) throws IOException {
        String[] splittedString = file.split("---");
        //Map<String,String> tokens = ParserYAML.getMetaDataFromString(splittedString[0]);
        String html = MarkdownParser.markdownToHtml(splittedString[1]);
        return html;
    }
}
