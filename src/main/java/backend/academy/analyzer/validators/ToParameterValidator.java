package backend.academy.analyzer.validators;

import lombok.extern.log4j.Log4j2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Log4j2
public class ToParameterValidator implements ParameterValidator {

    @Override
    public boolean isValid(String paramBody) {
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate.parse(paramBody, timeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            log.error("Переданный формат времени --to не валиден", e);
            return false;
        }
    }
}
