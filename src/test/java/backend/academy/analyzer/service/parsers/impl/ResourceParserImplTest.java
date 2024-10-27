package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.enums.PathType;
import backend.academy.analyzer.service.parsers.interfaces.ResourceParser;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourceParserImplTest {

    private static final String LOCAL_PATH =
        "./src/test/resources/logs/logs.txt";

    private static final String LOCAL_PATH_PATTERN_FOR_SINGLE_FILE =
        "./src/test/resources/logs/2024*.txt";

    private static final String LOCAL_PATH_PATTERN_FOR_MULTIPLE_FILE =
        "./src/test/resources/logs/logs*.txt";

    private static final String INVALID_LOCAL_PATH = "./hello/world*";

    private static final String URL_PATH =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

    private final ResourceParser parser = new ResourceParserImpl();

    @Test
    public void checkValidParseResourceNameByLocalPath() {
        assertEquals(List.of("logs.txt"), parser.parsePathResourceName(PathType.LOCAL, LOCAL_PATH));
    }

    @Test
    public void checkValidParseResourceNameByLocalPathPatternSingleFile() {
        assertEquals(List.of("2024example.txt"), parser.parsePathResourceName(PathType.LOCAL, LOCAL_PATH_PATTERN_FOR_SINGLE_FILE));
    }

    @Test
    public void checkValidParseResourceNameByLocalPathPatternMultipleFile() {
        assertEquals(List.of("logs.txt", "logs2.txt"), parser.parsePathResourceName(PathType.LOCAL, LOCAL_PATH_PATTERN_FOR_MULTIPLE_FILE));
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
