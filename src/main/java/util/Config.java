package util;

import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.HashMap;

public class Config {

    private static final com.typesafe.config.Config CONFIG = ConfigFactory.parseFile(new File("properties.conf"));
    private static final HashMap<Character, Integer> VALUES = getValues();

    public static final int DEPTH = CONFIG.getInt("branchDepth");

    public static final double NORMAL_MULTIPLIER = CONFIG.getDouble("evaluationMultipliers.normal");
    public static final double ATTACK_MULTIPLIER = CONFIG.getDouble("evaluationMultipliers.attack");
    public static final double DEFEND_MULTIPLIER = CONFIG.getDouble("evaluationMultipliers.defend");


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
