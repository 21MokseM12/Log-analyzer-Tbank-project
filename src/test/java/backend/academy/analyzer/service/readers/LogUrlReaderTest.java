package backend.academy.analyzer.service.readers;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogUrlReaderTest {

    private static final String CORRECT_URL =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

    private static final String INCORRECT_URL = "https://example.com";

    private static final String INVALID_URL = "a:";

    private final LogReader reader = new LogUrlReader();

    @Test
    public void checkReadAllLogsSuccess() {
        Stream<String> stream = reader.read(CORRECT_URL);
        assertTrue(stream.findAny().isPresent());
    }

    @Test
    public void checkReadAllLogsDenied() {
        Stream<String> stream = reader.read(INCORRECT_URL);
        assertTrue(stream.findAny().isPresent());
    }

    @Test
    public void checkThrowsRead() {
        assertThrows(NoSuchElementException.class, () -> {
            reader.read(INVALID_URL);
        });
    }
}
