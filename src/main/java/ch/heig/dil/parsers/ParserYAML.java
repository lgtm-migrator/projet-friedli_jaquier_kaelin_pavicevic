package ch.heig.dil.parsers;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant de parser le contenu d'un fichier YAML et récupérer les métadonnées
 *
 * @author Alexandre Jaquier
 */
public class ParserYAML {
    public static final String CONFIG_FILE = "config.yml";

    /**
     * @param sourcePath : chemin du fichier de config
     * @return la configuration du fichier YAML
     */
    public static Map<String, String> parseConfig(Path sourcePath) {
        Map<String, String> config = new HashMap<>();
        try {
            config = getMetaDataFromString(Files.readString(sourcePath.resolve(CONFIG_FILE)));
        } catch (Exception ignored) {
        }
        return config;
    }

    /**
     * Méthode statique permettant de parser une chaîne de caractères, provenant d'un fichier YAML
     *
     * @param yamlString contenu du fichier
     * @return Métadonnées du fichier YAML
     */
    public static Map<String, String> getMetaDataFromString(String yamlString) throws IOException {
        Map<String, String> meta = new HashMap<>();
        YAMLFactory factory = new YAMLFactory();
        YAMLParser parser = factory.createParser(yamlString);
        while (parser.nextToken() != null) {
            meta.put(parser.getCurrentName(), parser.getText());
        }
        meta.remove(null);
        return meta;
    }
}
