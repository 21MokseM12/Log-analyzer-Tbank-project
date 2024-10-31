package backend.academy.analyzer.service.analyzers;

import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.model.LogReport;
import backend.academy.analyzer.service.filters.LogFilter;
import backend.academy.analyzer.service.filters.LogFilterImpl;
import backend.academy.analyzer.service.parsers.interfaces.LogParser;
import backend.academy.analyzer.service.parsers.impl.LogParserImpl;
import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerAnalyzerTest {

    private static final String PATH = "./src/test/resources/logs/analyzer/test.txt";

    private final LogParser logParser = new LogParserImpl();

    private final LogFilter logFilter = new LogFilterImpl();

    private final Analyzer analyzer = new LoggerAnalyzer(logParser);

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkAnalyzeAllLogsSuccess() {
        Map<String, Integer> resources = Map.of("product_1", 7, "product_2", 6);
        Map<Integer, Integer> statusCodes = Map.of(304, 8, 404, 4, 200, 1);
        Map<String, Integer> clientIps = Map.of("93.180.71.3", 2, "46.4.66.76", 2, "80.91.33.133", 2);
        Map<String, Integer> userAgents = Map.of("Debian APT-HTTP/1.3", 12, "Go 1.1 package http", 1);

        LogReport firstResult = new LogReport(List.of("sources"), LocalDateTime.MIN, LocalDateTime.MAX,
            13, 138, 340,
            resources, statusCodes, clientIps, userAgents);
        LogReport secondResult = new LogReport(List.of("sources"), LocalDateTime.MIN, LocalDateTime.MAX,
            13, 138, 324,
            resources, statusCodes, clientIps, userAgents);

        Stream<Log> logs = logFilter.filterLogs(
            logParser.parseToLog(reader.read(PATH)),
            LocalDateTime.MIN,
            LocalDateTime.MAX,
            "",
            ""
        );
        LogReport report = analyzer.analyze(logs, List.of("sources"), LocalDateTime.MIN, LocalDateTime.MAX);
        assertTrue(report.equals(firstResult) || report.equals(secondResult));
    }
}
