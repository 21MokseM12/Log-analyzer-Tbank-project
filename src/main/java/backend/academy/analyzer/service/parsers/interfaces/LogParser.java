package backend.academy.analyzer.service.parsers.interfaces;

import backend.academy.analyzer.model.Log;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface LogParser {

    Stream<Log> parseToLog(Stream<String> logStrings, LocalDateTime from, LocalDateTime to);

    String parseLogRequestResource(String request);
}
