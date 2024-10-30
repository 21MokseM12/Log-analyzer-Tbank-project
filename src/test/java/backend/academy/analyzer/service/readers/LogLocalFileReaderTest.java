package backend.academy.analyzer.service.readers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogLocalFileReaderTest {

    private static Stream<Arguments> validPathPatterns() {
        return Stream.of(
            Arguments.of("./src/test/resources/logs/logs.txt"),
            Arguments.of("./src/test/resources/logs/log*"),
            Arguments.of("./src/test/resources/**/targetLogs.txt"),
            Arguments.of("./src/test/**/?ogs*")
        );
    }

    private static final String INVALID_PATH = "./src/test/resources/log/log.txt";


    private final LogReader reader = new LogLocalFileReader();

    @ParameterizedTest
    @MethodSource("validPathPatterns")
    public void checkReadDataMethod(String path) {
        assertTrue(reader.read(path).findAny().isPresent());
    }

    @Test
    public void checkThrowsRead() {
        assertThrows(NoSuchElementException.class, () -> {
            reader.read(INVALID_PATH);
        });
    }
}
