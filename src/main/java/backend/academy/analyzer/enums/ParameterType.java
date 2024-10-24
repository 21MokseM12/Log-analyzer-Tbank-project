package backend.academy.analyzer.enums;

public enum ParameterType {

    PATH("--path"), FROM("--from"), TO("--to"), FORMAT("--format");

    private final String type;

    ParameterType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
