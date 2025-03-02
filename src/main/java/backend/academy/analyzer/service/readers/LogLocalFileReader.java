package backend.academy.analyzer.service.readers;

import backend.academy.analyzer.service.parsers.impl.DirectoryPathParserImpl;
import backend.academy.analyzer.service.parsers.interfaces.DirectoryPathParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogLocalFileReader implements LogReader {

    @Override
    public Stream<String> read(String stringPath) {
        try {
            DirectoryPathParserImpl directoryParser = new DirectoryPathParserImpl(stringPath);
            if (directoryParser.beginWildcardIndex() != -1) {
                return findDataByFilePatternPath(directoryParser);
            } else {
                return findDataByFilePath(stringPath);
            }
        } catch (IOException e) {
            log.error("Указанный локальный путь не был найден", e);
            throw new NoSuchElementException();
        }
    }

    private Stream<String> findDataByFilePatternPath(DirectoryPathParser parser)
        throws IOException {
        Path parent = parser.getParentDirectory();
        String regexPattern = createRegexPattern(parser.getFileNamePattern());

        return Files.walk(parent)
            .filter(path -> !Files.isDirectory(path))
            .filter(path -> path.getFileName().toString().matches(regexPattern))
            .flatMap(p -> {
                try {
                    return Files.readAllLines(p).stream();
                } catch (IOException e) {
                    log.error("Ошибка чтения файла: {}", p, e);
                    return Stream.empty();
                }
            });
    }

    private String createRegexPattern(String path) {
        return path
            .replace("**", "")
            .replace("*", "[^/]*")
            .replace("?", ".");
    }

    private Stream<String> findDataByFilePath(String stringPath) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Path.of(stringPath));
        Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(
            reader.lines().iterator(),
            Spliterator.ORDERED
        );
        return StreamSupport.stream(spliterator, false);
    }
}
