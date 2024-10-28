package backend.academy.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LogReport {

    private List<String> sourceName;

    private LocalDateTime from;

    private LocalDateTime to;

    private long logCount;

    private long avgServerResponse;

    private long percentServerResponse;

    private Map<String, Integer> popularResources;

    private Map<Integer, Integer> popularStatusCodes;
}
