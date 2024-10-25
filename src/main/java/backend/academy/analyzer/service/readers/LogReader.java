package backend.academy.analyzer.service.readers;

import java.util.stream.Stream;

public interface LogReader {
    Stream<String> read(String stringPath);
}
