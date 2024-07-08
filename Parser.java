package console.parser;

import java.util.*;

public class Parser {

    public static ParsedString Parse(String inputString) {
        String function = getFunction(inputString);
        Map<String, String[]> args = getArgs(inputString);
        return new ParsedString(function, args);
    }

    private static String getFunction(String inputString) {
        inputString = inputString.replaceAll("\\s+", " ").trim();
        int index = inputString.indexOf('-');
        if (index == -1) return inputString;

        inputString = inputString.substring(0, index);
        String[] words = inputString.split(" ");
        return String.join(" ", words);
    }

    private static Map<String, String[]> getArgs(String inputString) {
        Map<String, String[]> args = new HashMap<>();

        inputString = inputString.replaceAll("\\s+", " ").trim();
        int index = inputString.indexOf('-') + 1;
        if (index == 0) return args;
        inputString = inputString.substring(index);

        for (String parts : inputString.split(" -")) {
            String[] words = parts.split(" ");

            String key = words[0];
            String[] values = Arrays.copyOfRange(words, 1, words.length, String[].class);

            args.put(key, values);
        }

        return args;
    }
}
