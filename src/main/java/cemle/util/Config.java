package cemle.util;

import com.typesafe.config.ConfigFactory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Config {

    private static final com.typesafe.config.Config CONFIG = getConfig();
    private static final HashMap<Character, Integer> VALUES = getValues();

    public static final int DEPTH = CONFIG.getInt("branchDepth");
    public static final double NORMAL_MULTIPLIER = CONFIG.getDouble("evaluationMultipliers.normal");
    public static final double ATTACK_MULTIPLIER = CONFIG.getDouble("evaluationMultipliers.attack");
    public static final double DEFEND_MULTIPLIER = CONFIG.getDouble("evaluationMultipliers.defend");

    private static com.typesafe.config.Config getConfig() {
        File configFile = new File("properties.conf");
        com.typesafe.config.Config config = ConfigFactory.parseFile(configFile);
        if (config.isEmpty()) {
            try {
                System.out.println("Config not set up, copying from sample");
                FileUtils.copyFile(new File("properties.conf.sample"), configFile);
                config = ConfigFactory.parseFile(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    private static HashMap<Character, Integer> getValues() {
        char[] pieces = new char[]{'p', 'r', 'n', 'b', 'q', 'k'};
        HashMap<Character, Integer> values = new HashMap<>();
        for (char piece : pieces) {
            values.put(Character.toUpperCase(piece), CONFIG.getInt(String.format("values.%c", piece)));
            values.put(piece, -CONFIG.getInt(String.format("values.%c", piece)));
        }
        return values;
    }

    public static int getPieceValue(char piece) {
        return VALUES.get(piece);
    }


}
