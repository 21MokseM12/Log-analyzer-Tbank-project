package backend.academy.analyzer.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathParameterValidatorTest {

    private static Stream<Arguments> validUrls() {
        return Stream.of(
            Arguments.of("https://example.com"),
            Arguments.of("https://youtube.com/"),
            Arguments.of("https://github.com/21MokseM12"),
            Arguments.of("https://leetcode.com/problemset/"),
            Arguments.of("https://mail.ru/")
        );
    }

    private static Stream<Arguments> invalidUrls() {
        return Stream.of(
            Arguments.of("hps://example.com"),
            Arguments.of("hps:/example.com"),
            Arguments.of("hps://example"),
            Arguments.of(""),
            Arguments.of("Hello, world"),
            Arguments.of("hps://.com")
        );
    }

    private static Stream<Arguments> validLocalPathsToSingleFiles() {
        return Stream.of(
            Arguments.of("./src/test/resources/logs/2024example.txt"),
            Arguments.of("./src/test/resources/logs/logs.txt"),
            Arguments.of("./src/test/resources/logs/logs2.txt")
        );
    }

    private static Stream<Arguments> invalidLocalPathsToSingleFiles() {
        return Stream.of(
            Arguments.of("./src/resources/logs/2024example.txt"),
            Arguments.of("/src/test/resources/logs/logs2.txt"),
            Arguments.of("")
        );
    }

    private static Stream<Arguments> validLocalPathsToMultipleFiles() {
        return Stream.of(
            Arguments.of("./src/resources/logs/2024*"),
            Arguments.of("/src/test/resources/logs/logs*"),
            Arguments.of("/src/test/resources/logs/*.txt")
        );
    }

    private final ParameterValidator validator = new PathParameterValidator();

    @ParameterizedTest
    @MethodSource("validUrls")
    public void checkValidUrl(String url) {
        assertTrue(validator.isValid(url));
    }

    @ParameterizedTest
    @MethodSource("invalidUrls")
    public void checkInvalidUrl(String url) {
        assertFalse(validator.isValid(url));
    }

    @ParameterizedTest
    @MethodSource("validLocalPathsToSingleFiles")
    public void checkValidLocalPathToSingleFile(String path) {
        assertTrue(validator.isValid(path));
    }

    @ParameterizedTest
    @MethodSource("invalidLocalPathsToSingleFiles")
    public void checkInvalidLocalPathToSingleFile(String path) {
        assertFalse(validator.isValid(path));
    }

    @ParameterizedTest
    @MethodSource("validLocalPathsToMultipleFiles")
    public void checkValidLocalPathToMultipleFile(String path) {
        assertTrue(validator.isValid(path));
    }
}
