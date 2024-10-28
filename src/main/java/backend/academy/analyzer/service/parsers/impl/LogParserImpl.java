package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.service.parsers.interfaces.LogParser;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("magicnumber")
@Log4j2
public class LogParserImpl implements LogParser {

    private static final int MAX_SPLIT_LOG_LENGTH = 8;

    @Override
    public Stream<Log> parseToLog(Stream<String> logStrings, LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter inputTimeFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);

        return logStrings
            .map(line -> line.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)(?=(?:[^\\[]*\\[[^]]*])*[^]]*$)"))
            .map(line -> {
                if (line.length <= MAX_SPLIT_LOG_LENGTH) {
                    log.warn("Лог некорректен: %s".formatted(line[3]));
                }

                line[3] = line[3].substring(1, line[3].length() - 1);
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(line[3], inputTimeFormat);

                return new Log(
                    line[0],
                    line[2],
                    zonedDateTime.toLocalDateTime(),
                    line[4],
                    Integer.parseInt(line[5]),
                    Integer.parseInt(line[6]),
                    line[6],
                    line[7]
                );
            })
            .filter(log -> !log.timeLocal().isBefore(from) && !log.timeLocal().isAfter(to));
    }

    @Override
    public String parseLogRequestResource(String request) {
        String resource = request.split(" ")[1];
        return resource.substring(resource.lastIndexOf('/') + 1);
    }
}
