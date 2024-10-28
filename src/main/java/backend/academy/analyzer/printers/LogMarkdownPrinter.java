package backend.academy.analyzer.printers;

import backend.academy.analyzer.enums.HttpStatusCodes;
import backend.academy.analyzer.enums.ReportTopic;
import backend.academy.analyzer.model.LogReport;
import java.time.LocalDateTime;
import java.util.Map;

public class LogMarkdownPrinter implements LogPrinter {

    private static final String REPORT_PATH = "./src/test/resources/reports/report.md";

    private static final String HEAD_DESIGNATION = "#### ";

    private static final String TABLE_HEAD_DELIMITER = ":---------------:";

    private static final String TABLE_DELIMITER = "|";

    private static final String DOUBLE_NEW_LINE = "\n\n";

    private static final String TABULATION_DOUBLE_SPACE = "\t  ";

    private static final String DOUBLE_TABULATION = "\t\t";

    private static final String TRIPLE_TABULATION = "\t\t\t";

    private static final String DOUBLE_TABULATION_DOUBLE_SPACE = "\t\t  ";

    private static final String TABULATION_FOUR_SPACE = "\t    ";

    @Override
    public void print(LogReport report) {
        String formattedReport = formatToMarkDown(report);
        this.printConsole(formattedReport);
        this.printFile(formattedReport, REPORT_PATH);
    }

    private String formatToMarkDown(LogReport report) {
        StringBuilder formattedReport = new StringBuilder();
        addGeneralTable(formattedReport, report);
        addResourceTable(formattedReport, report);
        addStatusCodeTable(formattedReport, report);

        return formattedReport.toString();
    }

    private void addGeneralTable(StringBuilder formattedReport, LogReport report) {
        String from = report.from().equals(LocalDateTime.MIN) ? "-" : report.from().toString();
        String to = report.to().equals(LocalDateTime.MAX) ? "-" : report.to().toString();

        formattedReport.append(HEAD_DESIGNATION).append(ReportTopic.GENERAL_INFO_LABEL).append(DOUBLE_NEW_LINE);
        formattedReport.append(TABLE_DELIMITER).append(TABULATION_DOUBLE_SPACE).append(ReportTopic.METRIC)
            .append(TABULATION_DOUBLE_SPACE).append(TABLE_DELIMITER)
            .append(TABULATION_DOUBLE_SPACE).append(ReportTopic.MEANING)
            .append(DOUBLE_TABULATION).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER)
            .append(TABULATION_DOUBLE_SPACE).append(ReportTopic.INPUT_RESOURCES_NAME).append(TABULATION_DOUBLE_SPACE)
            .append(TABLE_DELIMITER)
            .append(report.sourceName().toString(), 1, report.sourceName().toString().length() - 1)
            .append(TRIPLE_TABULATION).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append("  ").append(ReportTopic.BEGIN_DATE)
            .append(" ").append(TABLE_DELIMITER).append(DOUBLE_TABULATION_DOUBLE_SPACE).append(from)
            .append(TRIPLE_TABULATION).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append("  ").append(ReportTopic.END_DATE)
            .append("  ").append(TABLE_DELIMITER).append(DOUBLE_TABULATION_DOUBLE_SPACE).append(to)
            .append(TRIPLE_TABULATION).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(ReportTopic.LOG_COUNT)
            .append(TABLE_DELIMITER).append(TABULATION_DOUBLE_SPACE).append(report.logCount())
            .append(DOUBLE_TABULATION).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(ReportTopic.AVG_SERVER_RESPONSE)
            .append(TABLE_DELIMITER).append(TABULATION_DOUBLE_SPACE).append(report.avgServerResponse())
            .append(DOUBLE_TABULATION).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(ReportTopic.PERCENT_SERVER_RESPONSE)
            .append(TABLE_DELIMITER).append(TABULATION_DOUBLE_SPACE).append(report.percentServerResponse())
            .append(DOUBLE_TABULATION).append(TABLE_DELIMITER).append(DOUBLE_NEW_LINE);
    }

    private void addResourceTable(StringBuilder formattedReport, LogReport report) {
        formattedReport.append(HEAD_DESIGNATION)
            .append(ReportTopic.GENERAL_MOST_POPULAR_RESOURCES_LABEL).append(DOUBLE_NEW_LINE);
        formattedReport.append(TABLE_DELIMITER).append(TABULATION_DOUBLE_SPACE).append(ReportTopic.RESOURCE_NAME)
            .append(TABULATION_DOUBLE_SPACE).append(TABLE_DELIMITER)
            .append(TABULATION_DOUBLE_SPACE).append(ReportTopic.COUNT)
            .append("\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER).append(TABLE_DELIMITER).append("\n");
        for (Map.Entry<String, Integer> entry : report.popularResources().entrySet()) {
            formattedReport.append(TABLE_DELIMITER)
                .append(TABULATION_DOUBLE_SPACE).append(entry.getKey()).append(TABULATION_DOUBLE_SPACE)
                .append(TABLE_DELIMITER)
                .append(DOUBLE_TABULATION_DOUBLE_SPACE).append(entry.getValue()).append(TABULATION_FOUR_SPACE)
                .append(TABLE_DELIMITER).append("\n");
        }
        formattedReport.append("\n");
    }

    private void addStatusCodeTable(StringBuilder formattedReport, LogReport report) {
        formattedReport.append(HEAD_DESIGNATION)
            .append(ReportTopic.GENERAL_MOST_POPULAR_STATUS_CODES_LABEL).append(DOUBLE_NEW_LINE);
        formattedReport.append(TABLE_DELIMITER).append(TABULATION_DOUBLE_SPACE).append(ReportTopic.STATUS_CODE_VALUE)
            .append(DOUBLE_TABULATION_DOUBLE_SPACE).append(TABLE_DELIMITER)
            .append(DOUBLE_TABULATION_DOUBLE_SPACE).append(ReportTopic.STATUS_CODE_NAME)
            .append(TABULATION_FOUR_SPACE).append(TABLE_DELIMITER).append("\t ").append(ReportTopic.COUNT)
            .append(TABULATION_DOUBLE_SPACE).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER).append(TABLE_DELIMITER).append("\n");
        for (Map.Entry<Integer, Integer> entry : report.popularStatusCodes().entrySet()) {
            formattedReport.append(TABLE_DELIMITER)
                .append(TABULATION_FOUR_SPACE).append(entry.getKey()).append(DOUBLE_TABULATION_DOUBLE_SPACE)
                .append(TABLE_DELIMITER)
                .append(HttpStatusCodes.getStatusFromCode(entry.getKey())).append(TABULATION_FOUR_SPACE)
                .append(TABLE_DELIMITER)
                .append(TABULATION_DOUBLE_SPACE).append(entry.getValue()).append(TABULATION_FOUR_SPACE)
                .append(TABLE_DELIMITER).append("\n");
        }
        formattedReport.append("\n");
    }
}
