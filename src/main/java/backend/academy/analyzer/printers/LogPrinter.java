package backend.academy.analyzer.printers;

import backend.academy.analyzer.model.LogReport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;

public interface LogPrinter {
    void print(LogReport report);

    default void printConsole(String data) {
        PrintStream printer = System.out;
        printer.println(data);
    }

    @SneakyThrows
    default void printFile(String data, String path) {
        File reportFile = new File(path);
        BufferedWriter printer = new BufferedWriter(new FileWriter(reportFile, StandardCharsets.UTF_8));
        printer.write(data);
        printer.flush();
    }
}
