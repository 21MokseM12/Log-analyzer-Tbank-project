package backend.academy.analyzer.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PathTypeTest {

    private static Stream<Arguments> validLocalPaths() {
        return Stream.of(
            Arguments.of("./src/test/resources/logs/logs*"),
            Arguments.of("./src/test/resources/logs/logs.txt"),
            Arguments.of("./src/test/**/targetLogs.txt"),
            Arguments.of("./src/test/**/log*"),
            Arguments.of("./src/test/**/?ogs*")
        );
    }

    @ParameterizedTest
    @MethodSource("validLocalPaths")
    public void checkIdentifyMethodOfLocalFile(String path) {
        assertEquals(PathType.LOCAL, PathType.identify(path));
    }

    @Test
    public void checkIdentifyOfUrl() {
        String path =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        assertEquals(PathType.URL, PathType.identify(path));
    }

    @Test
    public void checkThrowsInvalidPath() {
        String path = "asdfwfwfdf";
        assertThrows(NoSuchElementException.class, () -> PathType.identify(path));
    }
}
