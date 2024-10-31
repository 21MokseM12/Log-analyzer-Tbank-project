package backend.academy.analyzer.service.parsers.interfaces;

import backend.academy.analyzer.model.Log;
import java.util.stream.Stream;

public interface LogParser {

    Stream<Log> parseToLog(Stream<String> logStrings);

    String parseLogRequestResourceName(String request);

    String parseUserAgent(String userAgentData);
}
