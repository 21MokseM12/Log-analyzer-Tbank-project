package backend.academy.analyzer.validators;

import backend.academy.analyzer.enums.FilterFields;

public class FilterFieldValidator implements ParameterValidator {

    @Override
    public boolean isValid(String paramBody) {
        for (FilterFields field : FilterFields.values()) {
            if (field.toString().equals(paramBody)) {
                return true;
            }
        }
        return false;
    }
}
