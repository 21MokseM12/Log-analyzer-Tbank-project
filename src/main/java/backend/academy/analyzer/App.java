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

@SuppressWarnings("uncommentedmain")
public final class App {

    private static final String REPORT_FILE_PATH_PATTERN = "./src/test/resources/";

    private static final PrintFormat DEFAULT_FORMAT_VALUE = PrintFormat.MARKDOWN;

    private static final Map<ParameterType, String> ARGUMENT_MAP = new HashMap<>();

    private static final ValidatorFactory VALIDATOR_FACTORY = new ValidatorFactory();

    private static final LogReportCreator LOG_REPORT_CREATOR = new LogReportCreatorImpl();

    private static final PrinterFactory PRINTER_FACTORY = new PrinterFactory(REPORT_FILE_PATH_PATTERN);

    private App() {
    }

    public static void main(String[] args) {
        if (args.length < 2 || args.length % 2 != 0) {
            throw new IllegalArgumentException("Параметры некорректны");
        }

        for (int i = 0; i < args.length; i += 2) {
            for (ParameterType type : ParameterType.values()) {
                if (type.toString().equals(args[i])) {
                    ARGUMENT_MAP.put(type, args[i + 1]);
                }
            }
        }

        if (!ARGUMENT_MAP.containsKey(ParameterType.PATH)) {
            throw new IllegalArgumentException("Параметр %s не найден".formatted(ParameterType.PATH.toString()));
        }

        for (Map.Entry<ParameterType, String> entry : ARGUMENT_MAP.entrySet()) {
            if (!VALIDATOR_FACTORY.get(entry.getKey()).isValid(entry.getValue())) {
                throw new IllegalArgumentException("Тело параметра %s некорректно".formatted(entry.getKey()));
            }
        }

        LogReport report = LOG_REPORT_CREATOR.create(ARGUMENT_MAP);

        PRINTER_FACTORY.get(
            PrintFormat.valueOf(
                ARGUMENT_MAP.getOrDefault(ParameterType.FORMAT, DEFAULT_FORMAT_VALUE.toString()).toUpperCase()
            )
        ).print(report);
    }
}
