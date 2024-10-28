package backend.academy.analyzer.printers;

import backend.academy.analyzer.enums.HttpStatusCodes;
import backend.academy.analyzer.enums.ReportTopic;
import backend.academy.analyzer.model.LogReport;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogAdocPrinter implements LogPrinter {

    private static final String reportPath = "./src/test/resources/reports/report.adoc";

    private static final String HEAD_DESIGNATION = "==== ";

    private static final String TABLE_START = """
        [cols="a,a", options="header"]
        |===
        """;

    private static final String TABLE_END = "|===";

    private static final String TABLE_DELIMITER = "|";

    @Override
    public void print(LogReport report) {
        String formattedReport = formatToAdoc(report);
        this.printConsole(formattedReport);
        this.printFile(formattedReport, reportPath);
    }

    private String formatToAdoc(LogReport report) {
        StringBuilder formattedReport = new StringBuilder();
        addGeneralTable(formattedReport, report);
        addResourceTable(formattedReport, report);
        addStatusCodeTable(formattedReport, report);

        return formattedReport.toString();
    }

    private void addGeneralTable(StringBuilder formattedReport, LogReport report) {
        String resourceNames= report.sourceName().toString();
        String from = report.from().equals(LocalDateTime.MIN) ? "-" : report.from().toString();
        String to = report.to().equals(LocalDateTime.MAX) ? "-" : report.to().toString();

        formattedReport.append(HEAD_DESIGNATION).append(ReportTopic.GENERAL_INFO_LABEL)
            .append("\n\n");
        formattedReport.append(TABLE_START);
        addFirstRowToLabel(formattedReport, ReportTopic.METRIC, ReportTopic.MEANING);
        addRowToTable(formattedReport, ReportTopic.INPUT_RESOURCES_NAME.toString(),
            resourceNames.substring(1, resourceNames.length()-1));
        addRowToTable(formattedReport, ReportTopic.BEGIN_DATE.toString(), from);
        addRowToTable(formattedReport, ReportTopic.END_DATE.toString(), to);
        addRowToTable(formattedReport, ReportTopic.LOG_COUNT.toString(),
            String.valueOf(report.logCount()));
        addRowToTable(formattedReport, ReportTopic.AVG_SERVER_RESPONSE.toString(),
            String.valueOf(report.avgServerResponse()));
        addRowToTable(formattedReport, ReportTopic.PERCENT_SERVER_RESPONSE.toString(),
            String.valueOf(report.percentServerResponse()));
        formattedReport.append(TABLE_END).append("\n\n");
    }

    private void addResourceTable(StringBuilder formattedReport, LogReport report) {
        formattedReport.append(HEAD_DESIGNATION).append(ReportTopic.GENERAL_MOST_POPULAR_RESOURCES_LABEL)
            .append("\n\n");
        formattedReport.append(TABLE_START);
        addFirstRowToLabel(formattedReport, ReportTopic.RESOURCE_NAME, ReportTopic.COUNT);
        for (Map.Entry<String, Integer> entry : report.popularResources().entrySet()) {
            addRowToTable(formattedReport, String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        formattedReport.append(TABLE_END).append("\n\n");
    }

    private void addStatusCodeTable(StringBuilder formattedReport, LogReport report) {
        formattedReport.append(HEAD_DESIGNATION).append(ReportTopic.GENERAL_MOST_POPULAR_STATUS_CODES_LABEL)
            .append("\n\n");
        formattedReport.append(TABLE_START);
        addFirstRowToLabel(formattedReport, ReportTopic.STATUS_CODE_VALUE, ReportTopic.STATUS_CODE_NAME, ReportTopic.COUNT);

        for (Map.Entry<Integer, Integer> entry : report.popularStatusCodes().entrySet()) {
            addRowToTable(formattedReport, String.valueOf(entry.getKey()),
                HttpStatusCodes.getStatusFromCode(entry.getKey()).toString(),
                String.valueOf(entry.getValue()));
        }
        formattedReport.append(TABLE_END).append("\n\n");
    }

    private void addFirstRowToLabel(StringBuilder builder, ReportTopic firstTopic, ReportTopic secondTopic) {
        builder.append(TABLE_DELIMITER).append(firstTopic)
            .append(TABLE_DELIMITER).append(secondTopic)
            .append(TABLE_DELIMITER).append("\n");
    }

    private void addFirstRowToLabel(StringBuilder builder, ReportTopic firstTopic, ReportTopic secondTopic, ReportTopic thirdTopic) {
        builder.append(TABLE_DELIMITER).append(firstTopic)
            .append(TABLE_DELIMITER).append(secondTopic)
            .append(TABLE_DELIMITER).append(thirdTopic)
            .append(TABLE_DELIMITER).append("\n");
    }

    private void addRowToTable(StringBuilder builder, String firstValue, String secondValue) {
        builder.append(TABLE_DELIMITER).append(firstValue)
            .append(TABLE_DELIMITER).append(secondValue)
            .append(TABLE_DELIMITER).append("\n");
    }

    private void addRowToTable(StringBuilder builder, String firstValue, String secondValue, String thirdValue) {
        builder.append(TABLE_DELIMITER).append(firstValue)
            .append(TABLE_DELIMITER).append(secondValue)
            .append(TABLE_DELIMITER).append(thirdValue)
            .append(TABLE_DELIMITER).append("\n");
    }
}
