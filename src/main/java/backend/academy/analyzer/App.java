package backend.academy.analyzer;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.enums.PrintFormat;
import backend.academy.analyzer.factories.PrinterFactory;
import backend.academy.analyzer.factories.ValidatorFactory;
import backend.academy.analyzer.model.LogReport;
import backend.academy.analyzer.service.creators.LogReportCreator;
import backend.academy.analyzer.service.creators.LogReportCreatorImpl;
import java.util.HashMap;
import java.util.Map;

public final class App {

    private static final PrintFormat DEFAULT_FORMAT_VALUE = PrintFormat.MARKDOWN;

    private static final Map<ParameterType, String> argumentMap = new HashMap<>();

    private static final ValidatorFactory VALIDATOR_FACTORY = new ValidatorFactory();

    private static final LogReportCreator LOG_REPORT_CREATOR = new LogReportCreatorImpl();

    private static final PrinterFactory printerFactory = new PrinterFactory();

    private App() {
    }

    public static void main(String[] args) {
        if (args.length < 2 || args.length % 2 != 0) {
            throw new IllegalArgumentException("Параметры некорректны");
        }

        for (int i = 0; i < args.length; i += 2) {
            for (ParameterType type : ParameterType.values()) {
                if (type.toString().equals(args[i])) {
                    argumentMap.put(type, args[i + 1]);
                }
            }
        }

        if (!argumentMap.containsKey(ParameterType.PATH)) {
            throw new IllegalArgumentException("Параметр %s не найден".formatted(ParameterType.PATH.toString()));
        }

        for (Map.Entry<ParameterType, String> entry : argumentMap.entrySet()) {
            if (!VALIDATOR_FACTORY.get(entry.getKey()).isValid(entry.getValue())) {
                throw new IllegalArgumentException("Тело параметра %s некорректно".formatted(entry.getKey()));
            }
        }

        LogReport report = LOG_REPORT_CREATOR.create(argumentMap);

        printerFactory.get(
            PrintFormat.valueOf(
                argumentMap.getOrDefault(ParameterType.FORMAT, DEFAULT_FORMAT_VALUE.toString()).toUpperCase()
            )
        ).print(report);
    }
}
