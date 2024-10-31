package backend.academy.analyzer.enums;

public enum FilterFields {

    METHOD("method"),

    AGENT("agent"),

    IP("ip");

    private final String field;

    FilterFields(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return this.field;
    }
}
