package backend.academy.analyzer.service.parsers;

import backend.academy.analyzer.service.parsers.impl.LogParserImpl;
import backend.academy.analyzer.service.parsers.interfaces.LogParser;
import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogParserImplTest {

    private static final String PATH = "./src/test/resources/logs/logs.txt";

    private static final String REQUEST_SAMPLE = "GET /downloads/product_1 HTTP/1.1";

    private final LogParser parser = new LogParserImpl();

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkParseAllLogsSuccess() {
        assertTrue(parser.parseToLog(reader.read(PATH),
            LocalDateTime.MIN,
            LocalDateTime.MAX
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseLogInTimeRangeSuccess() {
        assertTrue(parser.parseToLog(reader.read(PATH),
                LocalDateTime.of(2015, 5, 17, 8, 0, 0),
                LocalDateTime.of(2015, 5, 17, 10, 0, 0)
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseExactlyOneLog() {
        assertTrue(parser.parseToLog(reader.read(PATH),
                LocalDateTime.of(2015, 5, 17, 8, 5, 45),
                LocalDateTime.of(2015, 5, 17, 10, 5, 45)
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseWithInvalidTime() {
        assertFalse(parser.parseToLog(reader.read(PATH),
                LocalDateTime.of(2015, 5, 18, 8, 0, 0),
                LocalDateTime.of(2015, 5, 17, 10, 0, 0)
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseLogRequestResourceName() {
        assertEquals("product_1", parser.parseLogRequestResource(REQUEST_SAMPLE));
    }
}
