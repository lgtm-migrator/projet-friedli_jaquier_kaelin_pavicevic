package ch.heig.dil.parsers;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParserYAML {
    public static Map<String,String> Parse(String yamlString){
        Map<String,String> meta = new HashMap<>();
        YAMLFactory factory = new YAMLFactory();
        try(YAMLParser parser = factory.createParser(yamlString);){
            while (parser.nextToken() != null) {
                meta.put(parser.getCurrentName(), parser.getText());
            }
            meta.remove(null);
        } catch (IOException e){
        }
        System.out.println(meta);
        return meta;
    }
}
