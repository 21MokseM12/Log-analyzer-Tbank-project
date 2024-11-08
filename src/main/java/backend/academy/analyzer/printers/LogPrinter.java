package backend.academy.analyzer.printers;

import backend.academy.analyzer.enums.ReportTopic;
import backend.academy.analyzer.model.LogReport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class LogPrinter {

    private static final String REPORT_DIRECTORY = "reports/";

    private static final String REPORT_FILE_NAME_PATTERN = "report-";

    private final String reportPath;

    private final String reportFileExtension;

    public LogPrinter(String reportPath, String reportFileExtension) {
        this.reportPath = reportPath;
        this.reportFileExtension = reportFileExtension;
    }

    abstract String generalTable(LogReport report);

    abstract String resourceTable(LogReport report);

    abstract String statusCodeTable(LogReport report);

    abstract String activeUserIpsTable(LogReport report);

    abstract String userAgentsTable(LogReport report);

    public final void print(LogReport report) {
        String formattedReport = format(report);
        this.printConsole(formattedReport);
        this.printFile(formattedReport, reportPath);
    }

    protected String[][] getGeneralTableRows(LogReport report) {
        String resourceNames = report.sourceName().toString();
        resourceNames = resourceNames.substring(1, resourceNames.length() - 1);
        String from = getTimeOrDefault(report.from(), LocalDateTime.MIN);
        String to = getTimeOrDefault(report.to(), LocalDateTime.MAX);

        return new String[][] {
            {ReportTopic.INPUT_RESOURCES_NAME.toString(), resourceNames},
            {ReportTopic.BEGIN_DATE.toString(), from},
            {ReportTopic.END_DATE.toString(), to},
            {ReportTopic.LOG_COUNT.toString(), String.valueOf(report.logCount())},
            {ReportTopic.AVG_SERVER_RESPONSE.toString(), String.valueOf(report.avgServerResponse())},
            {ReportTopic.PERCENT_SERVER_RESPONSE.toString(), String.valueOf(report.percentServerResponse())}
        };
    }

    private String getTimeOrDefault(LocalDateTime reportTime, LocalDateTime limitTime) {
        return reportTime.equals(limitTime) ? "-" : reportTime.toString();
    }

    private String format(LogReport report) {
        StringBuilder formattedReport = new StringBuilder();
        formattedReport.append(generalTable(report))
            .append(resourceTable(report))
            .append(statusCodeTable(report))
            .append(activeUserIpsTable(report))
            .append(userAgentsTable(report));

        return formattedReport.toString();
    }

    private void printConsole(String data) {
        PrintStream printer = System.out;
        printer.println(data);
    }

    @SneakyThrows
    private void printFile(String data, String path) {
        String pathToDirectory = path.concat(REPORT_DIRECTORY);
        new File(pathToDirectory).mkdir();
        File reportFile = new File(pathToDirectory
            .concat(REPORT_FILE_NAME_PATTERN)
            .concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss")))
            .concat(reportFileExtension));
        BufferedWriter printer = new BufferedWriter(new FileWriter(reportFile, StandardCharsets.UTF_8));
        printer.write(data);
        printer.flush();
    }
}
