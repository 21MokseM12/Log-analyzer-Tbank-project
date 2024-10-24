package backend.academy.analyzer.validators;

import backend.academy.analyzer.enums.PrintFormat;

public class FormatParameterValidator implements ParameterValidator {

    @Override
    public boolean isValid(String paramBody) {
        for (PrintFormat format : PrintFormat.values()) {
            if (format.toString().equals(paramBody)) {
                return true;
            }
        }

        return false;
    }
}
