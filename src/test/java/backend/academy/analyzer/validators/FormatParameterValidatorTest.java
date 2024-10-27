package backend.academy.analyzer.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormatParameterValidatorTest {

    private static Stream<Arguments> validFormats() {
        return Stream.of(
            Arguments.of("markdown"),
            Arguments.of("adoc")
        );
    }

    private static Stream<Arguments> invalidFormats() {
        return Stream.of(
            Arguments.of("simple text"),
            Arguments.of(""),
            Arguments.of("11233"),
            Arguments.of("ados"),
            Arguments.of("mardown")
        );
    }

    private final ParameterValidator validator = new FormatParameterValidator();

    @ParameterizedTest
    @MethodSource("validFormats")
    public void checkValidFormat(String format) {
        assertTrue(validator.isValid(format));
    }

    @ParameterizedTest
    @MethodSource("invalidFormats")
    public void checkInvalidFormat(String format) {
        assertFalse(validator.isValid(format));
    }
}
