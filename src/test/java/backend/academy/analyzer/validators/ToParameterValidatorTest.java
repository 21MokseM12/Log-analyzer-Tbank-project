package backend.academy.analyzer.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToParameterValidatorTest {

    private static Stream<Arguments> validTime() {
        return Stream.of(
            Arguments.of("2024-08-31"),
            Arguments.of("2017-10-30"),
            Arguments.of("2003-02-01")
        );
    }

    private static Stream<Arguments> invalidTime() {
        return Stream.of(
            Arguments.of("31-08-2024"),
            Arguments.of("08-31-2024"),
            Arguments.of("2024-08-35"),
            Arguments.of("2024-16-31"),
            Arguments.of("456-08-31")
        );
    }

    private final ParameterValidator validator = new ToParameterValidator();

    @ParameterizedTest
    @MethodSource("validTime")
    public void checkValidTime(String time) {
        assertTrue(validator.isValid(time));
    }

    @ParameterizedTest
    @MethodSource("invalidTime")
    public void checkInvalidTime(String time) {
        assertFalse(validator.isValid(time));
    }
}
