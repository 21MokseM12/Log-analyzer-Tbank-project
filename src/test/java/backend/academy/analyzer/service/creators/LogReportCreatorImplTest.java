package backend.academy.analyzer.service.creators;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.model.LogReport;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogReportCreatorImplTest {

    private static final String PATH = "./src/test/resources/logs/logs.txt";

    private static final String FROM = "2015-05-17";

    private static final String TO = "2015-05-18";

    private final LogReportCreator creator = new LogReportCreatorImpl();

    @Test
    public void checkCreateReportMethodWithAllTimeParameters() {
        Map<ParameterType, String> map = Map.of(ParameterType.PATH, PATH,
            ParameterType.FROM, FROM,
            ParameterType.TO, TO);
        LogReport report = creator.create(map);
        assertNotNull(report);
    }

    @Test
    public void checkCreateReportMethodWithFromParameter() {
        Map<ParameterType, String> map = Map.of(ParameterType.PATH, PATH,
            ParameterType.FROM, FROM);
        LogReport report = creator.create(map);
        assertNotNull(report);
    }

    @Test
    public void checkCreateReportMethodWithToParameter() {
        Map<ParameterType, String> map = Map.of(ParameterType.PATH, PATH,
            ParameterType.TO, TO);
        LogReport report = creator.create(map);
        assertNotNull(report);
    }

    @Test
    public void checkCreateReportMethodWithoutTimeParameters() {
        Map<ParameterType, String> map = Map.of(ParameterType.PATH, PATH);
        LogReport report = creator.create(map);
        assertNotNull(report);
    }
}
