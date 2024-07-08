package console.parser;

import java.util.Map;

public class ParsedString {

    private final String function;
    private final Map<String, String[]> args;

    public ParsedString(String function, Map<String, String[]> args) {
        this.function = function;
        this.args = args;
    }

    public String getFunction() {
        return function;
    }

    public String getArgument(String key) {
        return getArgument(key, 0);
    }

    public String getArgument(String key, int index) {
        try {
            return args.get(key)[index];
        } catch (Exception e) {
            return null;
        }
    }
}
