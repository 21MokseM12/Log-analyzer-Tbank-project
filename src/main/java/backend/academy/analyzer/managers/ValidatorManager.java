package backend.academy.analyzer.managers;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.validators.FormatParameterValidator;
import backend.academy.analyzer.validators.FromParameterValidator;
import backend.academy.analyzer.validators.ParameterValidator;
import backend.academy.analyzer.validators.PathParameterValidator;
import backend.academy.analyzer.validators.ToParameterValidator;
import java.util.HashMap;
import java.util.Map;

public class ValidatorManager {

    private final Map<ParameterType, ParameterValidator> validators = new HashMap<>();

    public ValidatorManager() {
        for (ParameterType type : ParameterType.values()) {
            if (type.equals(ParameterType.PATH)) {
                validators.put(type, new PathParameterValidator());
            } else if (type.equals(ParameterType.FROM)) {
                validators.put(type, new FromParameterValidator());
            } else if (type.equals(ParameterType.TO)) {
                validators.put(type, new ToParameterValidator());
            } else if (type.equals(ParameterType.FORMAT)) {
                validators.put(type, new FormatParameterValidator());
            }
        }
    }

    public ParameterValidator get(ParameterType type) {
        return validators.get(type);
    }

}
