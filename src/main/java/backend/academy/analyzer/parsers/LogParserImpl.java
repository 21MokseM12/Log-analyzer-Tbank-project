package backend.academy.analyzer.parsers;

import backend.academy.analyzer.model.Log;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Log4j2
public class LogParserImpl implements LogParser {

    @Override
    public Stream<Log> parse(Stream<String> logStrings, LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter inputTimeFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);

        return logStrings
            .map(line -> line.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)(?=(?:[^\\[]*\\[[^]]*])*[^]]*$)"))
            .map(line -> {
                if (line.length < 9) {
                    log.warn("Лог некорректен: %s".formatted(line[3]));
                }

                line[3] = line[3].substring(1, line[3].length()-1);
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
}
