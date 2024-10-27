package backend.academy.analyzer.validators;

import backend.academy.analyzer.enums.ParameterType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DateRangeValidator {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public LocalDateTime getStartOfRange(Map<ParameterType, String> argumentMap) {
        if (argumentMap.containsKey(ParameterType.FROM)) {
            return LocalDate.parse(argumentMap.get(ParameterType.FROM), timeFormatter).atStartOfDay();
        } else {
            return LocalDateTime.MIN;
        }
    }

    public LocalDateTime getEndOfRange(Map<ParameterType, String> argumentMap) {
        if (argumentMap.containsKey(ParameterType.TO)) {
            return LocalDate.parse(argumentMap.get(ParameterType.TO), timeFormatter).atStartOfDay();
        } else {
            return LocalDateTime.MAX;
        }
    }
}
