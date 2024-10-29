package backend.academy.analyzer.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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

    private Map<String, Integer> activeClientsIps;

    private Map<String, Integer> popularUserAgents;
}
