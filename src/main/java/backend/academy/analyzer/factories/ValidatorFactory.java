package backend.academy.analyzer.factories;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.validators.FilterFieldValidator;
import backend.academy.analyzer.validators.FilterValueValidator;
import backend.academy.analyzer.validators.FormatParameterValidator;
import backend.academy.analyzer.validators.FromParameterValidator;
import backend.academy.analyzer.validators.ParameterValidator;
import backend.academy.analyzer.validators.PathParameterValidator;
import backend.academy.analyzer.validators.ToParameterValidator;
import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {

    private final Map<ParameterType, ParameterValidator> validators = new HashMap<>();

    public ValidatorFactory() {
        for (ParameterType type : ParameterType.values()) {
            if (type.equals(ParameterType.PATH)) {
                validators.put(type, new PathParameterValidator());
            } else if (type.equals(ParameterType.FROM)) {
                validators.put(type, new FromParameterValidator());
            } else if (type.equals(ParameterType.TO)) {
                validators.put(type, new ToParameterValidator());
            } else if (type.equals(ParameterType.FORMAT)) {
                validators.put(type, new FormatParameterValidator());
            } else if (type.equals(ParameterType.FILTER_FIELD)) {
                validators.put(type, new FilterFieldValidator());
            } else if (type.equals(ParameterType.FILTER_VALUE)) {
                validators.put(type, new FilterValueValidator());
            }
        }
    }

    public ParameterValidator get(ParameterType type) {
        return validators.get(type);
    }

}
