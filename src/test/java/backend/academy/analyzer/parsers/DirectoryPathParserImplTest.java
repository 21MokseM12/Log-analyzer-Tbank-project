package backend.academy.analyzer.parsers;

import backend.academy.analyzer.parsers.impl.DirectoryPathParserImpl;
import backend.academy.analyzer.parsers.interfaces.DirectoryPathParser;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectoryPathParserImplTest {

    private static final String PATH_WITHOUT_WILDCARDS = "./src/test/resources/logs/logs.txt";

    private static final String PATH_WITH_WILDCARDS = "./src/test/resources/logs/logs*";

    @Test
    public void checkGetParentDirectoryByPathWithoutWildcards() {
        DirectoryPathParser parser = new DirectoryPathParserImpl(PATH_WITHOUT_WILDCARDS);
        assertEquals(Path.of(PATH_WITHOUT_WILDCARDS), parser.getParentDirectory());
    }

    @Test
    public void checkGetParentDirectoryByPathWithWildcards() {
        DirectoryPathParser parser = new DirectoryPathParserImpl(PATH_WITH_WILDCARDS);
        assertEquals(Path.of(PATH_WITH_WILDCARDS.substring(0, PATH_WITH_WILDCARDS.lastIndexOf("/"))), parser.getParentDirectory());
    }

    @Test
    public void checkGetPathPatternByPathWithWildcards() {
        DirectoryPathParser parser = new DirectoryPathParserImpl(PATH_WITH_WILDCARDS);
        assertEquals(PATH_WITH_WILDCARDS.substring(PATH_WITH_WILDCARDS.lastIndexOf("/") + 1), parser.getPathPattern());
    }
}
