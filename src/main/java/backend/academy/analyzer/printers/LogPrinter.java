package backend.academy.analyzer.printers;

import backend.academy.analyzer.model.LogReport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.SneakyThrows;

public abstract class LogPrinter {

    private final String reportPath;

    public LogPrinter(String reportPath) {
        this.reportPath = reportPath;
    }

    abstract String generalTable(LogReport report);

    abstract String resourceTable(LogReport report);

    abstract String statusCodeTable(LogReport report);

    abstract String activeUserIpsTable(LogReport report);

    abstract String userAgentsTable(LogReport report);

    protected String getTimeOrDefault(LocalDateTime reportTime, LocalDateTime limitTime) {
        return reportTime.equals(limitTime) ? "-" : reportTime.toString();
    }

    public final void print(LogReport report) {
        String formattedReport = format(report);
        this.printConsole(formattedReport);
        this.printFile(formattedReport, reportPath);
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
        File reportFile = new File(path);
        BufferedWriter printer = new BufferedWriter(new FileWriter(reportFile, StandardCharsets.UTF_8));
        printer.write(data);
        printer.flush();
    }
}
