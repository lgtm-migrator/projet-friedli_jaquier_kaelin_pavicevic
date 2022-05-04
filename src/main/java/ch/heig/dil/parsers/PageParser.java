package ch.heig.dil.parsers;

import java.io.IOException;

public class PageParser {
    public static String parse(String file) throws IOException {
        String[] splitString = file.split("---");
        if (splitString.length != 2)
            throw new IOException("The splitting string is not in the file");
        return MarkdownParser.markdownToHtml(splitString[1]);
    }
}
