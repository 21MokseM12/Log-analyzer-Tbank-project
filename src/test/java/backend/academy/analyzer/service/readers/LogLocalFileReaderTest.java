package backend.academy.analyzer.service.readers;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogLocalFileReaderTest {

    private static final String CORRECT_PATH_TO_SINGLE_FILE =
        "./src/test/resources/logs/logs.txt";

    private static final String CORRECT_PATH_TO_MULTIPLE_FILES =
        "./src/test/resources/logs/2024*.txt";

    private static final String INVALID_PATH = "./src/test/resources/log/log.txt";

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkReadAllLogsSuccess() {
        Stream<String> stream = reader.read(CORRECT_PATH_TO_SINGLE_FILE);
        assertTrue(stream.findAny().isPresent());
    }

    @Test
    public void checkReadMultipleFilesByWildCardSuccess() {
        Stream<String> stream = reader.read(CORRECT_PATH_TO_MULTIPLE_FILES);
        assertTrue(stream.findAny().isPresent());
    }

    @Test
    public void checkThrowsRead() {
        assertThrows(NoSuchElementException.class, () -> {
            reader.read(INVALID_PATH);
        });
    }
}
