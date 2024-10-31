package backend.academy.analyzer.validators;

public class FilterValueValidator implements ParameterValidator {

    @Override
    public boolean isValid(String paramBody) {
        return !paramBody.isBlank();
    }
}
