package backend.academy.analyzer.parsers;

import backend.academy.analyzer.model.Log;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface LogParser {
    Stream<Log> parse(Stream<String> logStrings, LocalDateTime from, LocalDateTime to);
}
