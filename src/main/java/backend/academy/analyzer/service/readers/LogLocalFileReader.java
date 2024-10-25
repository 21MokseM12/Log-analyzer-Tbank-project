package backend.academy.analyzer.service.readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogLocalFileReader implements LogReader {

    @Override
    public Stream<String> read(String stringPath) {
        try {
            Path path = Path.of(stringPath);
            return Files.readAllLines(path).stream();
        } catch (IOException e) {
            log.error("Указанный локальный путь не был найден", e);
            throw new NoSuchElementException();
        }
    }
}
