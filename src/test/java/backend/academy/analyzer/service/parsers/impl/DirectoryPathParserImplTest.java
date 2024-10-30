package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.service.parsers.interfaces.DirectoryPathParser;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectoryPathParserImplTest {

    private static Stream<Arguments> validParentDirectories() {
        return Stream.of(
            Arguments.of("./src/test/resources/logs/logs.txt", Path.of("./src/test/resources/logs/logs.txt")),
            Arguments.of("./src/test/resources/logs/logs*", Path.of("./src/test/resources/logs/")),
            Arguments.of("./src/test/resources/**/logs*", Path.of("./src/test/resources/")),
            Arguments.of("./src/test/?esources/**/logs*", Path.of("./src/test/"))
        );
    }

    private static Stream<Arguments> validFileNamePatterns() {
        return Stream.of(
            Arguments.of("./src/test/resources/logs/logs*", "logs*"),
            Arguments.of("./src/test/resources/**/logs*", "logs*"),
            Arguments.of("./src/test/?esources/**/*.txt", "*.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("validParentDirectories")
    public void checkGetParentDirectory(String path, Path expected) {
        DirectoryPathParser parser = new DirectoryPathParserImpl(path);
        assertEquals(expected, parser.getParentDirectory());
    }

    @ParameterizedTest
    @MethodSource("validFileNamePatterns")
    public void checkGetFileNamePattern(String path, String expected) {
        DirectoryPathParser parser = new DirectoryPathParserImpl(path);
        assertEquals(expected, parser.getFileNamePattern());
    }

//    @Test
//    public void checkGetPathPatternByFileNameWithWildcards() {
//        DirectoryPathParser parser = new DirectoryPathParserImpl(PATH_WITH_WILDCARDS);
//        assertEquals(PATH_WITH_WILDCARDS.substring(PATH_WITH_WILDCARDS.lastIndexOf("/") + 1), parser.getFileNamePattern());
//    }
}
