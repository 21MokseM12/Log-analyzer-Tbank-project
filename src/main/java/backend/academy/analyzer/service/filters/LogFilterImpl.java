package backend.academy.analyzer.service.filters;

import backend.academy.analyzer.enums.FilterFields;
import backend.academy.analyzer.model.Log;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class LogFilterImpl implements LogFilter {

    @Override
    public Stream<Log> filterLogs(Stream<Log> logs,
        LocalDateTime from, LocalDateTime to,
        String filterField, String filterValue) {
        return logs
            .filter(log -> filterByValue(log, filterField, filterValue))
            .filter(log -> !log.timeLocal().isBefore(from) && !log.timeLocal().isAfter(to));
    }

    private boolean filterByValue(Log log, String filterField, String filterValue) {
        if (filterField.isEmpty() || filterValue.isEmpty()) {
            return true;
        }

        boolean filterFlag = false;
        for (FilterFields field : FilterFields.values()) {
            if (field.toString().equals(filterField)) {
                if (field.equals(FilterFields.AGENT)) {
                    filterFlag = filterByAgent(log, filterValue);
                } else if (field.equals(FilterFields.METHOD)) {
                    filterFlag = filterByMethod(log, filterValue);
                } else if (field.equals(FilterFields.IP)) {
                    filterFlag = filterByIp(log, filterValue);
                }
            }
        }
        return filterFlag;
    }

    private boolean filterByAgent(Log log, String agentPattern) {
        String userAgent = log.httpUserAgent();
        return userAgent.substring(1, userAgent.length() - 1).matches(agentPattern)
            || userAgent.substring(1, userAgent.length() - 1).contains(agentPattern);
    }

    private boolean filterByMethod(Log log, String methodPattern) {
        String method = log.request();
        return method.substring(1, method.length() - 1).matches(methodPattern)
            || method.substring(1, method.length() - 1).contains(methodPattern);
    }

    private boolean filterByIp(Log log, String ipPattern) {
        return log.remoteAddr().matches(ipPattern)
            || log.remoteAddr().contains(ipPattern);
    }
}
