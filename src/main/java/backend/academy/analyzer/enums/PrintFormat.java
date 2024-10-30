package backend.academy.analyzer.enums;

import lombok.Getter;

public enum PrintFormat {
    MARKDOWN("markdown", ".md"), ADOC("adoc", ".adoc");

    private final String format;

    @Getter private final String fileType;

    PrintFormat(String format, String fileType) {
        this.format = format;
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return this.format;
    }
}
