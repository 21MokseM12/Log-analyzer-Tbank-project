package backend.academy.analyzer.service.analyzers;

import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.model.LogReport;
import backend.academy.analyzer.parsers.interfaces.LogParser;
import backend.academy.analyzer.parsers.impl.LogParserImpl;
import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerAnalyzerTest {

    private static final String PATH = "./src/test/resources/logs/analyzer/test.txt";

    private final LogParser logParser = new LogParserImpl();

    private final Analyzer analyzer = new LoggerAnalyzer(logParser);

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkAnalyzeAllLogsSuccess() {
        LogReport result = new LogReport(List.of("sources"), LocalDateTime.MIN, LocalDateTime.MAX,
            13, 138, 340,
            Map.of("product_1", 7, "product_2", 6), Map.of(304, 8, 404, 4, 200, 1));
        Stream<Log> logs = logParser.parseToLog(reader.read(PATH), LocalDateTime.MIN, LocalDateTime.MAX);
        LogReport report = analyzer.analyze(logs, List.of("sources"), LocalDateTime.MIN, LocalDateTime.MAX);
        assertEquals(result, report);
    }
}
