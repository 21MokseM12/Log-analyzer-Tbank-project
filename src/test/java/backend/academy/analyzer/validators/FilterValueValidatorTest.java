package backend.academy.analyzer.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterValueValidatorTest {

    private final ParameterValidator validator = new FilterValueValidator();

    private static Stream<Arguments> validFilterValues() {
        return Stream.of(
            Arguments.of("GET"),
            Arguments.of("\"Mozzilla*\""),
            Arguments.of("123.456.789.101"),
            Arguments.of("\"555\""),
            Arguments.of("\"\""),
            Arguments.of("Hello, world!"),
            Arguments.of("\"Hello, world!\""),
            Arguments.of("123.456.?89.?01"),
            Arguments.of("G?T")
        );
    }

    @ParameterizedTest
    @MethodSource("validFilterValues")
    public void checkValidFilterValues(String value) {
        assertTrue(validator.isValid(value));
    }

    @Test
    public void checkInvalidFilterValues() {
        assertFalse(validator.isValid(""));
    }
}
