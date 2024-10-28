package backend.academy.analyzer.printers;

import backend.academy.analyzer.model.LogReport;
import lombok.SneakyThrows;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

public interface LogPrinter {
    void print(LogReport report);



    default void printConsole(String data) {
        PrintStream printer = System.out;
        printer.println(data);
    }

    @SneakyThrows
    default void printFile(String data, String path) {
        File reportFile = new File(path);
        BufferedWriter printer = new BufferedWriter(new FileWriter(reportFile));
        printer.write(data);
        printer.flush();
    }
}
