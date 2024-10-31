package backend.academy.analyzer.service.filters;

import backend.academy.analyzer.model.Log;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface LogFilter {
    Stream<Log> filterLogs(Stream<Log> logs, LocalDateTime from, LocalDateTime to,
        String filterField, String filterValue);
}
