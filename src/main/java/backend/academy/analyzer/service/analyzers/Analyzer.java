package backend.academy.analyzer.service.analyzers;

import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.model.LogReport;
import java.util.stream.Stream;

public interface Analyzer {
    LogReport analyze(Stream<Log> logs);
}
