package backend.academy.analyzer.service.readers;

import backend.academy.analyzer.service.parsers.impl.DirectoryPathParserImpl;
import java.io.IOException;
import java.nio.file.DirectoryStream;
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
            DirectoryPathParserImpl directoryParser = new DirectoryPathParserImpl(stringPath);
            if (directoryParser.beginWildcardIndex() != -1) {
                Path parent = directoryParser.getParentDirectory();
                Stream<String> readLines = Stream.empty();
                try (DirectoryStream<Path> directoryStream =
                         Files.newDirectoryStream(parent, directoryParser.getPathPattern())) {
                    for (Path entry : directoryStream) {
                        readLines = Stream.concat(readLines, Files.readAllLines(entry).stream());
                    }
                    return readLines;
                }
            } else {
                return Files.readAllLines(Path.of(stringPath)).stream();
            }
        } catch (IOException e) {
            log.error("Указанный локальный путь не был найден", e);
            throw new NoSuchElementException();
        }
    }
}
