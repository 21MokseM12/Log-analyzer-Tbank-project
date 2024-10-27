package backend.academy.analyzer.factories;

import backend.academy.analyzer.enums.PrintFormat;
import backend.academy.analyzer.printers.LogAdocPrinter;
import backend.academy.analyzer.printers.LogMarkdownPrinter;
import backend.academy.analyzer.printers.LogPrinter;
import java.util.HashMap;
import java.util.Map;

public class PrinterFactory {

    private final Map<PrintFormat, LogPrinter> printers = new HashMap<>();

    public PrinterFactory() {
        for (PrintFormat format : PrintFormat.values()) {
            if (format.equals(PrintFormat.MARKDOWN)) {
                printers.put(format, new LogMarkdownPrinter());
            } else if (format.equals(PrintFormat.ADOC)) {
                printers.put(format, new LogAdocPrinter());
            }
        }
    }

    public LogPrinter get(PrintFormat format) {
        return printers.get(format);
    }
}
