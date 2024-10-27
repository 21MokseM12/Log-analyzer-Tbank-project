package backend.academy.analyzer.enums;

public enum PrintFormat {
    MARKDOWN("markdown"), ADOC("adoc");

    private final String format;

    PrintFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return this.format;
    }
}
