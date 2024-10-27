package backend.academy.analyzer.parsers;

import java.nio.file.Path;

public interface DirectoryPathParser {
    Path getParentDirectory();
    String getPathPattern();
}
