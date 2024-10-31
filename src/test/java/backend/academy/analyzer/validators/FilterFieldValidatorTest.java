package backend.academy.analyzer.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterFieldValidatorTest {

    private final ParameterValidator validator = new FilterFieldValidator();

    private static Stream<Arguments> validFields() {
        return Stream.of(
            Arguments.of("method"),
            Arguments.of("ip"),
            Arguments.of("agent")
        );
    }

    private static Stream<Arguments> invalidFields() {
        return Stream.of(
            Arguments.of("methods"),
            Arguments.of(""),
            Arguments.of("hello, World!"),
            Arguments.of("1321312")
        );
    }

    @ParameterizedTest
    @MethodSource("validFields")
    public void checkValidFieldsTest(String field) {
        assertTrue(validator.isValid(field));
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    public void checkInvalidFieldsTest(String field) {
        assertFalse(validator.isValid(field));
    }
}
