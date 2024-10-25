package backend.academy.analyzer.factories;

import backend.academy.analyzer.enums.PathType;
import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import backend.academy.analyzer.service.readers.LogUrlReader;
import java.util.HashMap;
import java.util.Map;

public class LogReaderFactory {

    private final Map<PathType, LogReader> readers = new HashMap<>();

    public LogReaderFactory() {
        for (PathType type : PathType.values()) {
            if (type.equals(PathType.LOCAL)) {
                readers.put(type, new LogLocalFileReader());
            } else if (type.equals(PathType.URL)) {
                readers.put(type, new LogUrlReader());
            }
        }
    }

    public LogReader get(PathType type) {
        return readers.get(type);
    }
}
