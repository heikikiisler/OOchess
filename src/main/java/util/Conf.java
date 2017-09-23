package util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.HashMap;

public class Conf {

    private static final Config CONFIG = ConfigFactory.parseFile(new File("properties.conf"));
    private static final HashMap<Character, Integer> VALUES = getValues();

    private static HashMap<Character, Integer> getValues() {
        char[] pieces = new char[]{'p', 'r', 'n', 'b', 'q', 'k'};
        HashMap<Character, Integer> values = new HashMap<>();
        for (char piece: pieces) {
            values.put(Character.toUpperCase(piece), CONFIG.getInt(String.format("values.%c", piece)));
            values.put(piece, -CONFIG.getInt(String.format("values.%c", piece)));
        }
        return values;
    }

    public static int getPieceValue(char piece) {
        return VALUES.get(piece);
    }


}
