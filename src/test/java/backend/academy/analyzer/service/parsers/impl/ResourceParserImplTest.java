package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.enums.PathType;
import backend.academy.analyzer.service.parsers.interfaces.ResourceParser;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourceParserImplTest {

    private static Stream<Arguments> validLocalPathPatterns() {
        return Stream.of(
            Arguments.of("./src/test/resources/logs/logs.txt", List.of("logs.txt")),
            Arguments.of("./src/test/resources/logs/2024*", List.of("2024example.txt")),
            Arguments.of("./src/test/resources/logs/logs*", List.of("logs.txt", "logs2.txt")),
            Arguments.of("./src/test/resources/**/targetLogs.txt", List.of("targetLogs.txt")),
            Arguments.of("./src/test/**/?ogs*", List.of("logs.txt", "logs2.txt"))
        );
    }

    private static final String INVALID_LOCAL_PATH = "./hello/world*";

    private static final String URL_PATH =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

    private final ResourceParser parser = new ResourceParserImpl();

    @ParameterizedTest
    @MethodSource("validLocalPathPatterns")
    public void checkValidParseResourceNames(String path, List<String> expected) {
        assertEquals(expected, parser.parsePathResourceName(PathType.LOCAL, path).stream().sorted().toList());
    }

    @Test
    public void checkThrowsExceptionIfLocalFileNotFound() {
        assertThrows(NoSuchElementException.class, () -> parser.parsePathResourceName(PathType.LOCAL, INVALID_LOCAL_PATH));
    }

    @Test
    public void checkValidParseResourceNameByUrl() {
        assertEquals(List.of("nginx_logs"), parser.parsePathResourceName(PathType.URL, URL_PATH));
    }
}
