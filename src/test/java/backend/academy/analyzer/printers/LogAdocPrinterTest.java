package backend.academy.analyzer.printers;

import backend.academy.analyzer.enums.ReportTopic;
import backend.academy.analyzer.model.LogReport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogAdocPrinterTest {

    private LogAdocPrinter logAdocPrinter;
    private LogReport mockReport;

    @BeforeEach
    void setUp() {
        logAdocPrinter = Mockito.spy(new LogAdocPrinter());
        mockReport = mock(LogReport.class);

        when(mockReport.sourceName()).thenReturn(List.of("source1", "source2"));
        when(mockReport.from()).thenReturn(LocalDateTime.of(2022, 1, 1, 0, 0));
        when(mockReport.to()).thenReturn(LocalDateTime.of(2022, 12, 31, 23, 59));
        when(mockReport.logCount()).thenReturn(100L);
        when(mockReport.avgServerResponse()).thenReturn((long) 150.0);
        when(mockReport.percentServerResponse()).thenReturn((long) 95.0);
        when(mockReport.popularResources()).thenReturn(Map.of("resource1", 50, "resource2", 30));
        when(mockReport.popularStatusCodes()).thenReturn(Map.of(200, 75, 404, 15));
    }

    @Test
    void testPrint_GeneratesCorrectAdocOutput() {
        // Перехватываем аргумент, переданный в printConsole
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        logAdocPrinter.print(mockReport);

        // Проверяем, что метод printConsole был вызван
        verify(logAdocPrinter).printConsole(captor.capture());

        String result = captor.getValue();

        // Проверка основных секций и метрик
        assertTrue(result.contains("==== " + ReportTopic.GENERAL_INFO_LABEL));
        assertTrue(result.contains("|%s|source1, source2".formatted(ReportTopic.INPUT_RESOURCES_NAME)));
        assertTrue(result.contains("|%s|2022-01-01T00:00".formatted(ReportTopic.BEGIN_DATE)));
        assertTrue(result.contains("|%s|2022-12-31T23:59".formatted(ReportTopic.END_DATE)));
        assertTrue(result.contains("|%s|100".formatted(ReportTopic.LOG_COUNT)));
        assertTrue(result.contains("|%s|150".formatted(ReportTopic.AVG_SERVER_RESPONSE)));
        assertTrue(result.contains("|%s|95".formatted(ReportTopic.PERCENT_SERVER_RESPONSE)));

        assertTrue(result.contains("==== " + ReportTopic.GENERAL_MOST_POPULAR_RESOURCES_LABEL));
        assertTrue(result.contains("|resource1|50"));
        assertTrue(result.contains("|resource2|30"));

        assertTrue(result.contains("==== " + ReportTopic.GENERAL_MOST_POPULAR_STATUS_CODES_LABEL));
        assertTrue(result.contains("|200|OK|75"));
        assertTrue(result.contains("|404|Not Found|15"));
    }
}


