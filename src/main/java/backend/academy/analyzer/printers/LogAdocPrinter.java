package backend.academy.analyzer.printers;

import backend.academy.analyzer.enums.HttpStatusCodes;
import backend.academy.analyzer.enums.ReportTopic;
import backend.academy.analyzer.model.LogReport;
import java.time.LocalDateTime;
import java.util.Map;

public class LogAdocPrinter extends LogPrinter {

    private static final String HEAD_DESIGNATION = "==== ";

    private static final String DOUBLE_PATTERN = "| %-30s | %-30s |";

    private static final String TRIPLE_PATTERN = "| %-25s | %-25s | %-25s |";

    private static final String TABLE_START = """
        [cols="a,a", options="header"]
        |===
        """;

    private static final String TABLE_END = "|===";

    private static final String DOUBLE_NEW_LINE = "\n\n";

    private static final String NEW_LINE = "\n";

    public LogAdocPrinter(String reportPath) {
        super(reportPath);
    }

    @Override
    String generalTable(LogReport report) {
        StringBuilder builder = new StringBuilder();

        String resourceNames = report.sourceName().toString();
        String from = super.getTimeOrDefault(report.from(), LocalDateTime.MIN);
        String to = super.getTimeOrDefault(report.to(), LocalDateTime.MAX);

        String[][] rows = new String[][] {
            {ReportTopic.INPUT_RESOURCES_NAME.toString(), resourceNames},
            {ReportTopic.BEGIN_DATE.toString(), from},
            {ReportTopic.END_DATE.toString(), to},
            {ReportTopic.LOG_COUNT.toString(), String.valueOf(report.logCount())},
            {ReportTopic.AVG_SERVER_RESPONSE.toString(), String.valueOf(report.avgServerResponse())},
            {ReportTopic.PERCENT_SERVER_RESPONSE.toString(), String.valueOf(report.percentServerResponse())}
        };

        builder.append(tableLabel(ReportTopic.GENERAL_INFO_LABEL))
            .append(TABLE_START)
            .append(rowTableByPattern(DOUBLE_PATTERN, ReportTopic.METRIC.toString(), ReportTopic.MEANING.toString()));
        for (String[] row : rows) {
            builder.append(rowTableByPattern(DOUBLE_PATTERN, row[0], row[1]));
        }
        builder.append(TABLE_END).append(DOUBLE_NEW_LINE);
        return builder.toString();
    }

    @Override
    String resourceTable(LogReport report) {
        StringBuilder builder = new StringBuilder();

        builder
            .append(tableLabel(ReportTopic.GENERAL_MOST_POPULAR_RESOURCES_LABEL))
            .append(TABLE_START)
            .append(rowTableByPattern(DOUBLE_PATTERN,
                ReportTopic.RESOURCE_NAME.toString(), ReportTopic.COUNT.toString()));

        for (Map.Entry<String, Integer> entry : report.popularResources().entrySet()) {
            builder.append(rowTableByPattern(DOUBLE_PATTERN,
                String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        builder.append(TABLE_END).append(DOUBLE_NEW_LINE);
        return builder.toString();
    }

    @Override
    String statusCodeTable(LogReport report) {
        StringBuilder builder = new StringBuilder();

        builder
            .append(tableLabel(ReportTopic.GENERAL_MOST_POPULAR_STATUS_CODES_LABEL))
            .append(TABLE_START)
            .append(rowTableByPattern(TRIPLE_PATTERN,
                ReportTopic.STATUS_CODE_VALUE.toString(), ReportTopic.STATUS_CODE_NAME.toString(),
                ReportTopic.COUNT.toString()));

        for (Map.Entry<Integer, Integer> entry : report.popularStatusCodes().entrySet()) {
            builder.append(rowTableByPattern(TRIPLE_PATTERN,
                String.valueOf(entry.getKey()),
                HttpStatusCodes.getStatusFromCode(entry.getKey()),
                String.valueOf(entry.getValue())));
        }
        builder.append(TABLE_END).append(DOUBLE_NEW_LINE);
        return builder.toString();
    }

    @Override
    String activeUserIpsTable(LogReport report) {
        StringBuilder builder = new StringBuilder();

        builder
            .append(tableLabel(ReportTopic.GENERAL_ACTIVE_USER_IPS))
            .append(TABLE_START)
            .append(rowTableByPattern(DOUBLE_PATTERN,
                ReportTopic.USER_IP_LABEL.toString(), ReportTopic.COUNT.toString()));

        for (Map.Entry<String, Integer> entry : report.activeClientsIps().entrySet()) {
            builder.append(rowTableByPattern(DOUBLE_PATTERN,
                String.valueOf(entry.getKey()),
                String.valueOf(entry.getValue())));
        }
        builder.append(TABLE_END).append(DOUBLE_NEW_LINE);
        return builder.toString();
    }

    @Override
    String userAgentsTable(LogReport report) {
        StringBuilder builder = new StringBuilder();

        builder
            .append(tableLabel(ReportTopic.GENERAL_MOST_POPULAR_USER_AGENTS))
            .append(TABLE_START)
            .append(rowTableByPattern(DOUBLE_PATTERN,
                ReportTopic.USER_AGENT_LABEL.toString(), ReportTopic.COUNT.toString()));

        for (Map.Entry<String, Integer> entry : report.popularUserAgents().entrySet()) {
            builder.append(rowTableByPattern(DOUBLE_PATTERN,
                String.valueOf(entry.getKey()),
                String.valueOf(entry.getValue())));
        }
        builder.append(TABLE_END).append(DOUBLE_NEW_LINE);
        return builder.toString();
    }

    private String tableLabel(ReportTopic topic) {
        return HEAD_DESIGNATION.concat(topic.toString().concat(DOUBLE_NEW_LINE));
    }

    private String rowTableByPattern(String pattern, Object... params) {
        return String.format(pattern, params).concat(NEW_LINE);
    }
}
