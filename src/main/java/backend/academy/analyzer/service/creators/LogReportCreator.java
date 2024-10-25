package backend.academy.analyzer.service.creators;

import backend.academy.analyzer.enums.ParameterType;
import backend.academy.analyzer.model.LogReport;
import java.util.Map;

public interface LogReportCreator {
    LogReport create(Map<ParameterType, String> map);
}
