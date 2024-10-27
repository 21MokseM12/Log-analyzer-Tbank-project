package backend.academy.analyzer.validators;

import backend.academy.analyzer.enums.ParameterType;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateRangeValidatorTest {

    private final DateRangeValidator validator = new DateRangeValidator();

    @Test
    public void checkGetStartOfRange() {
        Map<ParameterType, String> map = new HashMap<>();
        map.put(ParameterType.FROM, "2024-08-31");
        assertEquals(LocalDateTime.of(2024, 8, 31, 0, 0), validator.getStartOfRange(map));
    }

    @Test
    public void checkGetStartOfRangeDefault() {
        Map<ParameterType, String> map = new HashMap<>();
        assertEquals(LocalDateTime.MIN, validator.getStartOfRange(map));
    }

    @Test
    public void checkGetEndOfRange() {
        Map<ParameterType, String> map = new HashMap<>();
        map.put(ParameterType.TO, "2022-10-01");
        assertEquals(LocalDateTime.of(2022, 10, 1, 0, 0), validator.getEndOfRange(map));
    }

    @Test
    public void checkGetEndOfRangeDefault() {
        Map<ParameterType, String> map = new HashMap<>();
        assertEquals(LocalDateTime.MAX, validator.getEndOfRange(map));
    }
}
