package backend.academy.analyzer.enums;

import lombok.Getter;

public enum PrintFormat {
    MARKDOWN("markdown", ".md"), ADOC("adoc", ".adoc");

    private final String format;

    @Getter private final String fileExtension;

    PrintFormat(String format, String fileExtension) {
        this.format = format;
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return this.format;
    }
}
