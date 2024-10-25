package backend.academy.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LogReport {

    private List<String> sourceName;

    @Nullable
    private LocalDateTime from;

    @Nullable
    private LocalDateTime to;

    private long logCount;

    private double avgServerResponse;

    private double percentServerResponse;

    Map<String, Integer> popularResources;

    Map<String, Integer> popularStatusCodes;

    public LogReport(List<String> sourceName, @Nullable LocalDateTime from, @Nullable LocalDateTime to) {
        this.sourceName = sourceName;
        this.from = from;
        this.to = to;
    }
}
