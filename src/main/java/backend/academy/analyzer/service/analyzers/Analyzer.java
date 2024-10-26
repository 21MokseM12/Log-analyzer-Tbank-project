package backend.academy.analyzer.service.analyzers;

import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.model.LogReport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface Analyzer {
    LogReport analyze(Stream<Log> logs, List<String> sources, LocalDateTime from, LocalDateTime to);
}
