package backend.academy.analyzer.parsers;

import backend.academy.analyzer.enums.PathType;
import java.util.List;

public interface ResourceParser {
    List<String> parsePathResourceName(PathType type, String stringPath);
}