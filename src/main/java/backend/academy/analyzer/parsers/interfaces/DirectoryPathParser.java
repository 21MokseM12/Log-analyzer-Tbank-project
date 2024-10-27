package backend.academy.analyzer.parsers.interfaces;

import java.nio.file.Path;

public interface DirectoryPathParser {
    Path getParentDirectory();
    String getPathPattern();
}
