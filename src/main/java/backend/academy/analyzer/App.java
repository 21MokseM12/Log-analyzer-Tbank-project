package backend.academy.analyzer;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.enums.PrintFormat;
import backend.academy.analyzer.managers.ValidatorManager;
import java.util.HashMap;
import java.util.Map;

public final class App {

//    private static final String DEFAULT_FORMAT_VALUE = "markdown";

    private static final Map<ParameterType, String> argumentMap = new HashMap<>();

    private static final ValidatorManager validatorManager = new ValidatorManager();

//    private static final Analyzer analyzer = new LoggerAnalyzer();
//
//    private static final PrinterManager printerManager = new PrinterManager();

    private App() {}

    public static void main(String[] args) {
        if (args.length < 2 || args.length % 2 != 0) {
            throw new IllegalArgumentException("Параметры некорректны");
        }

        for (int i = 0; i < args.length; i+=2) {
            for (ParameterType type : ParameterType.values()) {
                if (type.toString().equals(args[i])) {
                    argumentMap.put(type, args[i+1]);
                }
            }
        }

        for (Map.Entry<ParameterType, String> entry : argumentMap.entrySet()) {
            if (!validatorManager.get(entry.getKey()).isValid(entry.getValue())) {
                throw new IllegalArgumentException("Тело параметра %s некорректно".formatted(entry.getKey()));
            }
        }

//        Statistic statistic = analyzer.analyze(argumentMap);
//
//        printerManager.get(
//            PrintFormat.valueOf(
//                argumentMap.getOrDefault("--format", DEFAULT_FORMAT_VALUE).toUpperCase()
//            )
//        ).print();
    }
}
