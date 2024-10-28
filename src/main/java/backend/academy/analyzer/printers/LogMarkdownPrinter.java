package backend.academy.analyzer.printers;

import backend.academy.analyzer.enums.HttpStatusCodes;
import backend.academy.analyzer.enums.ReportTopic;
import backend.academy.analyzer.model.LogReport;
import java.time.LocalDateTime;
import java.util.Map;

public class LogMarkdownPrinter implements LogPrinter {

    private static final String reportPath = "./src/test/resources/reports/report.md";

    private static final String HEAD_DESIGNATION = "#### ";

    private static final String TABLE_HEAD_DELIMITER = ":---------------:";

    private static final String TABLE_DELIMITER = "|";

    @Override
    public void print(LogReport report) {
        String formattedReport = formatToMarkDown(report);
        this.printConsole(formattedReport);
        this.printFile(formattedReport, reportPath);
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

        formattedReport.append(HEAD_DESIGNATION).append(ReportTopic.GENERAL_INFO_LABEL).append("\n\n");
        formattedReport.append(TABLE_DELIMITER).append("\t  ").append(ReportTopic.METRIC)
            .append("\t  ").append(TABLE_DELIMITER).append("\t  ").append(ReportTopic.MEANING)
            .append("\t\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER).append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER)
            .append("\t  ").append(ReportTopic.INPUT_RESOURCES_NAME).append("\t  ")
            .append(TABLE_DELIMITER)
            .append(report.sourceName().toString(), 1, report.sourceName().toString().length()-1)
            .append("\t\t\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append("  ").append(ReportTopic.BEGIN_DATE)
            .append(" ").append(TABLE_DELIMITER).append("\t\t  ").append(from)
            .append("\t\t\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append("  ").append(ReportTopic.END_DATE)
            .append("  ").append(TABLE_DELIMITER).append("\t\t  ").append(to)
            .append("\t\t\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(ReportTopic.LOG_COUNT)
            .append(TABLE_DELIMITER).append("\t  ").append(report.logCount())
            .append("\t\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(ReportTopic.AVG_SERVER_RESPONSE)
            .append(TABLE_DELIMITER).append("\t  ").append(report.avgServerResponse())
            .append("\t\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(ReportTopic.PERCENT_SERVER_RESPONSE)
            .append(TABLE_DELIMITER).append("\t  ").append(report.percentServerResponse())
            .append("\t\t").append(TABLE_DELIMITER).append("\n\n");
    }

    private void addResourceTable(StringBuilder formattedReport, LogReport report) {
        formattedReport.append(HEAD_DESIGNATION)
            .append(ReportTopic.GENERAL_MOST_POPULAR_RESOURCES_LABEL).append("\n\n");
        formattedReport.append(TABLE_DELIMITER).append("\t  ").append(ReportTopic.RESOURCE_NAME)
            .append("\t  ").append(TABLE_DELIMITER).append("\t  ").append(ReportTopic.COUNT)
            .append("\t").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER).append(TABLE_DELIMITER).append("\n");
        for (Map.Entry<String, Integer> entry : report.popularResources().entrySet()) {
            formattedReport.append(TABLE_DELIMITER)
                .append("\t  ").append(entry.getKey()).append("\t  ")
                .append(TABLE_DELIMITER)
                .append("\t\t  ").append(entry.getValue()).append("\t    ")
                .append(TABLE_DELIMITER).append("\n");
        }
        formattedReport.append("\n");
    }

    private void addStatusCodeTable(StringBuilder formattedReport, LogReport report) {
        formattedReport.append(HEAD_DESIGNATION)
            .append(ReportTopic.GENERAL_MOST_POPULAR_STATUS_CODES_LABEL).append("\n\n");
        formattedReport.append(TABLE_DELIMITER).append("\t  ").append(ReportTopic.STATUS_CODE_VALUE)
            .append("\t\t  ").append(TABLE_DELIMITER).append("\t\t  ").append(ReportTopic.STATUS_CODE_NAME)
            .append("\t    ").append(TABLE_DELIMITER).append("\t ").append(ReportTopic.COUNT)
            .append("\t  ").append(TABLE_DELIMITER).append("\n");
        formattedReport.append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER)
            .append(TABLE_DELIMITER).append(TABLE_HEAD_DELIMITER).append(TABLE_DELIMITER).append("\n");
        for (Map.Entry<Integer, Integer> entry : report.popularStatusCodes().entrySet()) {
            formattedReport.append(TABLE_DELIMITER)
                .append("\t    ").append(entry.getKey()).append("\t\t  ")
                .append(TABLE_DELIMITER)
                .append(HttpStatusCodes.getStatusFromCode(entry.getKey())).append("\t    ")
                .append(TABLE_DELIMITER)
                .append("\t  ").append(entry.getValue()).append("\t    ")
                .append(TABLE_DELIMITER).append("\n");
        }
        formattedReport.append("\n");
    }
}
