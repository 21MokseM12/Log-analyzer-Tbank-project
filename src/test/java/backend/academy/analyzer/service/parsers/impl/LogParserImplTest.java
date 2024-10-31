package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.service.parsers.interfaces.LogParser;
import backend.academy.analyzer.service.readers.LogLocalFileReader;
import backend.academy.analyzer.service.readers.LogReader;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogParserImplTest {

    private static final String PATH = "./src/test/resources/logs/logs.txt";

    private static final String REQUEST_SAMPLE = "GET /downloads/product_1 HTTP/1.1";

    private static Stream<Arguments> validUserAgentPart() {
        return Stream.of(
            Arguments.of("\"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0\"", "Mozilla/5.0"),
            Arguments.of("\"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"", "Debian APT-HTTP/1.3"),
            Arguments.of("\"Chef Client/11.12.2 (ruby-1.9.3-p484; ohai-7.0.2; x86_64-linux; +http://opscode.com)\"", "Chef Client/11.12.2"),
            Arguments.of("\"urlgrabber/3.10 yum/3.4.3\"", "urlgrabber/3.10 yum/3.4.3"),
            Arguments.of("\"-\"", "-")
        );
    }

    private final LogParser parser = new LogParserImpl();

    private final LogReader reader = new LogLocalFileReader();

    @Test
    public void checkParseAllLogsSuccess() {
        assertTrue(parser.parseToLog(reader.read(PATH))
            .findAny()
            .isPresent());
    }

    @Test
    public void checkParseLogRequestResourceNameName() {
        assertEquals("product_1", parser.parseLogRequestResourceName(REQUEST_SAMPLE));
    }

    @ParameterizedTest
    @MethodSource("validUserAgentPart")
    public void checkParseUserAgentMethod(String userAgent, String expected) {
        assertEquals(expected, parser.parseUserAgent(userAgent));
    }
}
