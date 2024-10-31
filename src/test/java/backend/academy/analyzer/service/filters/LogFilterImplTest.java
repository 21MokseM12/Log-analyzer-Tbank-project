package backend.academy.analyzer.service.filters;

import backend.academy.analyzer.enums.FilterFields;
import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.service.parsers.impl.LogParserImpl;
import backend.academy.analyzer.service.parsers.interfaces.LogParser;
import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogFilterImplTest {

    private static final String PATH = "./src/test/resources/logs/logs.txt";

    private final LogFilter filter = new LogFilterImpl();

    private final LogParser parser = new LogParserImpl();

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkFilterLogInTimeRangeSuccess() {
        assertTrue(
            filter.filterLogs(
                parser.parseToLog(reader.read(PATH)),
                LocalDateTime.of(2015, 5, 22, 2, 0, 0),
                LocalDateTime.of(2015, 5, 23, 10, 0, 0),
                "",
                ""
            ).findAny().isPresent());
    }

    @Test
    public void checkFilterExactlyOneLog() {
        assertTrue(
            filter.filterLogs(
                parser.parseToLog(reader.read(PATH)),
                LocalDateTime.of(2015, 5, 22, 2, 5, 0),
                LocalDateTime.of(2015, 5, 22, 2, 5, 0),
                "",
                ""
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkFilterWithInvalidTime() {
        assertFalse(
            filter.filterLogs(
                parser.parseToLog(reader.read(PATH)),
                LocalDateTime.of(2015, 5, 18, 8, 0, 0),
                LocalDateTime.of(2015, 5, 17, 10, 0, 0),
                "",
                ""
            )
            .findAny()
            .isPresent());
    }

    @Test
    public void checkCorrectLogConverting() {
        List<Log> logs = filter.filterLogs(
            parser.parseToLog(reader.read(PATH)),
            LocalDateTime.of(2015, 5, 22, 2, 5, 0),
            LocalDateTime.of(2015, 5, 22, 2, 5, 0),
            "",
            "")
            .toList();

        List<Log> result = List.of(new Log("184.168.128.52", "-",
            LocalDateTime.of(2015, 5, 22, 2, 5, 0), "\"GET /downloads/product_2 HTTP/1.1\"",
            200, 951, "\"-\"", "\"urlgrabber/3.9.1 yum/3.2.29\""));

        assertEquals(result, logs);
    }

    @Test
    public void checkFilterByAgentValue() {
        List<Log> expected = List.of(new Log("184.168.128.52", "-",
            LocalDateTime.of(2015, 5, 22, 2, 5, 0),
            "\"GET /downloads/product_2 HTTP/1.1\"", 200, 951,
            "\"-\"", "\"urlgrabber/3.9.1 yum/3.2.29\""));

        List<Log> logs = filter.filterLogs(
            parser.parseToLog(reader.read(PATH)),
            LocalDateTime.MIN,
            LocalDateTime.MAX,
            FilterFields.AGENT.toString(),
            "urlgrabber"
        ).toList();

        assertEquals(expected, logs);
    }

    @Test
    public void checkFilterByMethodValue() {
        List<Log> expected = List.of(new Log("54.86.8.9", "-",
            LocalDateTime.of(2015, 5, 22, 2, 5, 31),
            "\"POST /downloads/product_1 HTTP/1.1\"", 404, 337,
            "\"-\"", "\"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\""));

        List<Log> logs = filter.filterLogs(
            parser.parseToLog(reader.read(PATH)),
            LocalDateTime.MIN,
            LocalDateTime.MAX,
            FilterFields.METHOD.toString(),
            "POST"
        ).toList();

        assertEquals(expected, logs);
    }

    @Test
    public void checkFilterByIpValue() {
        List<Log> expected = List.of(new Log("184.168.128.52", "-",
            LocalDateTime.of(2015, 5, 22, 2, 5, 0),
            "\"GET /downloads/product_2 HTTP/1.1\"", 200, 951,
            "\"-\"", "\"urlgrabber/3.9.1 yum/3.2.29\""));

        List<Log> logs = filter.filterLogs(
            parser.parseToLog(reader.read(PATH)),
            LocalDateTime.MIN,
            LocalDateTime.MAX,
            FilterFields.IP.toString(),
            "184.168.128.52"
        ).toList();

        assertEquals(expected, logs);
    }
}
