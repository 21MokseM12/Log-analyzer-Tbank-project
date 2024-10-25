package backend.academy.analyzer.parsers;

import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogParserImplTest {

    private static final String path = "./src/test/resources/logs/logs.txt";

    private final LogParser parser = new LogParserImpl();

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkParseAllLogsSuccess() {
        assertTrue(parser.parse(reader.read(path),
            LocalDateTime.MIN,
            LocalDateTime.MAX
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseLogInTimeRangeSuccess() {
        assertTrue(parser.parse(reader.read(path),
                LocalDateTime.of(2015, 5, 17, 8, 0, 0),
                LocalDateTime.of(2015, 5, 17, 10, 0, 0)
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseExactlySingleLog() {
        assertTrue(parser.parse(reader.read(path),
                LocalDateTime.of(2015, 5, 17, 8, 5, 45),
                LocalDateTime.of(2015, 5, 17, 10, 5, 45)
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseWithInvalidTime() {
        assertFalse(parser.parse(reader.read(path),
                LocalDateTime.of(2015, 5, 18, 8, 0, 0),
                LocalDateTime.of(2015, 5, 17, 10, 0, 0)
            )
            .findAny()
            .isPresent());
    }
}
