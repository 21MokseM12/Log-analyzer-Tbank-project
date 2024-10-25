package backend.academy.analyzer.validators;

import backend.academy.analyzer.enums.ParameterType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DateRangeValidator {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    public LocalDateTime getStartOfRange(Map<ParameterType, String> argumentMap) {
        if (argumentMap.containsKey(ParameterType.FROM)) {
            return LocalDateTime.parse(argumentMap.get(ParameterType.FROM), timeFormatter);
        } else {
            return LocalDateTime.MIN;
        }
    }

    public LocalDateTime getEndOfRange(Map<ParameterType, String> argumentMap) {
        if (argumentMap.containsKey(ParameterType.TO)) {
            return LocalDateTime.parse(argumentMap.get(ParameterType.TO), timeFormatter);
        } else {
            return LocalDateTime.MAX;
        }
    }
}
