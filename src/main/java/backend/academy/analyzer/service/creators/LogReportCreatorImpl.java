package backend.academy.analyzer.service.creators;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.enums.PathType;
import backend.academy.analyzer.factories.LogReaderFactory;
import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.model.LogReport;
import backend.academy.analyzer.parsers.interfaces.LogParser;
import backend.academy.analyzer.parsers.impl.LogParserImpl;
import backend.academy.analyzer.parsers.interfaces.ResourceParser;
import backend.academy.analyzer.parsers.impl.ResourceParserImpl;
import backend.academy.analyzer.service.analyzers.Analyzer;
import backend.academy.analyzer.service.analyzers.LoggerAnalyzer;
import backend.academy.analyzer.validators.DateRangeValidator;
import java.util.Map;
import java.util.stream.Stream;

public class LogReportCreatorImpl implements LogReportCreator {

    private final LogReaderFactory readerFactory = new LogReaderFactory();

    private final DateRangeValidator dateRangeValidator = new DateRangeValidator();

    private final LogParser logParser = new LogParserImpl();

    private final ResourceParser resourceParser = new ResourceParserImpl();

    private final Analyzer analyzer = new LoggerAnalyzer(logParser);

    @Override
    public LogReport create(Map<ParameterType, String> map) {
        PathType pathType = PathType.identify(map.get(ParameterType.PATH));

        Stream<String> logStrings = readerFactory
            .get(pathType)
            .read(map.get(ParameterType.PATH));
        Stream<Log> logs = logParser.parseToLog(
            logStrings,
            dateRangeValidator.getStartOfRange(map),
            dateRangeValidator.getEndOfRange(map)
        );
        return analyzer.analyze(
            logs,
            resourceParser.parsePathResourceName(pathType, map.get(ParameterType.PATH)),
            dateRangeValidator.getStartOfRange(map),
            dateRangeValidator.getEndOfRange(map)
            );
    }
}
