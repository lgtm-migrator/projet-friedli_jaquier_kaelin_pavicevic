package ch.heig.dil.parsers;

import java.io.IOException;

public class PageParser {
    public static String parse(String file) throws IOException {
        String[] splittedString = file.split("\n---\n");
        if (splittedString.length != 2)
            throw new IOException("The splitting string is not in the file");
        return MarkdownParser.markdownToHtml(splittedString[1]);
    }
}
