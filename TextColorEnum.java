package console.enumerations;

public enum TextColorEnum {
    ANSI_RESET("\u001B[0m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PINK("\u001B[38;5;201m"),
    ;

    private final String colorANSI;

    TextColorEnum(String colorANSI) {
        this.colorANSI = colorANSI;
    }

    public String getColorANSI() {
        return colorANSI;
    }
}
