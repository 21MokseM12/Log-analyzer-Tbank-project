package backend.academy.analyzer.service.analyzers;

import backend.academy.analyzer.model.Log;
import backend.academy.analyzer.model.LogReport;
import backend.academy.analyzer.service.parsers.interfaces.LogParser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoggerAnalyzer implements Analyzer {

    private static final float PERCENTIL_VALUE = 0.95f;

    private static final int TOP_LEVEL_VALUE = 3;

    private long logCount;

    private long avgServerResponse;

    private long percentServerResponse;

    private Map<String, Integer> popularResources;

    private Map<Integer, Integer> popularStatusCodes;

    private Map<String, Integer> activeClientsIps;

    private Map<String, Integer> popularUserAgents;

    private final LogParser logParser;

    public LoggerAnalyzer(LogParser logParser) {
        this.logParser = logParser;
    }

    @Override
    public LogReport analyze(Stream<Log> logs, List<String> sources, LocalDateTime from, LocalDateTime to) {
        initializeStates();

        List<Integer> percentileCandidates = new ArrayList<>();
        Map<String, Integer> resourcesMap = new HashMap<>();
        Map<Integer, Integer> statusCodesMap = new HashMap<>();
        Map<String, Integer> clientIpMap = new HashMap<>();
        Map<String, Integer> userAgentMap = new HashMap<>();

        analyzeLogsSelection(logs, percentileCandidates,
            resourcesMap, statusCodesMap,
            clientIpMap, userAgentMap);
        calculateAvgServerResponse();
        calculatePercentServerResponse(percentileCandidates);
        sortResources(resourcesMap);
        sortStatusCodes(statusCodesMap);
        sortActiveUsers(clientIpMap);
        sortUserAgents(userAgentMap);

        return new LogReport(sources, from, to,
            logCount, avgServerResponse, percentServerResponse,
            popularResources, popularStatusCodes,
            activeClientsIps, popularUserAgents);
    }

    private void initializeStates() {
        this.logCount = 0;
        this.avgServerResponse = 0;
        this.percentServerResponse = 0;
        this.popularResources = new LinkedHashMap<>();
        this.popularStatusCodes = new LinkedHashMap<>();
        this.activeClientsIps = new LinkedHashMap<>();
        this.popularUserAgents = new LinkedHashMap<>();
    }

    private void analyzeLogsSelection(
        Stream<Log> logs, List<Integer> percentileCandidates,
        Map<String, Integer> resourcesMap, Map<Integer, Integer> statusCodesMap,
        Map<String, Integer> clientIpMap, Map<String, Integer> userAgentMap
    ) {
        logs
            .forEach(log -> {
                logCount++;
                avgServerResponse += log.responseSize();

                String resourceName = logParser.parseLogRequestResourceName(log.request());
                resourcesMap.merge(resourceName, 1, Integer::sum);

                int statusCode = log.httpStatus();
                statusCodesMap.merge(statusCode, 1, Integer::sum);

                String clientIp = log.remoteAddr();
                clientIpMap.merge(clientIp, 1, Integer::sum);

                String userAgent = logParser.parseUserAgent(log.httpUserAgent());
                userAgentMap.merge(userAgent, 1, Integer::sum);

                // Резервный отбор кандидатов для перцентиля
                if (percentileCandidates.size() < Math.round(logCount * PERCENTIL_VALUE)) {
                    percentileCandidates.add(log.responseSize());
                } else if (Math.random() < Math.round(logCount * PERCENTIL_VALUE) / (double) logCount) {
                    int replaceIndex = (int) (Math.random() * percentileCandidates.size());
                    percentileCandidates.set(replaceIndex, log.responseSize());
                }
            });
    }

    private void calculateAvgServerResponse() {
        avgServerResponse = Math.round((double) avgServerResponse / logCount);
    }

    private void calculatePercentServerResponse(List<Integer> percentileCandidates) {
        if (logCount == 0) {
            return;
        }
        // Подсчет перцентиля на основе резервного набора кандидатов
        percentServerResponse = percentileCandidates.stream()
            .sorted()
            .skip((long) Math.round(percentileCandidates.size() * PERCENTIL_VALUE) - 1)
            .findFirst()
            .orElse(0);
    }

    private void sortResources(Map<String, Integer> resourcesMap) {
        this.popularResources = resourcesMap
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(TOP_LEVEL_VALUE)
            .collect(
                Collectors
                    .toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                    )
            );
    }

    private void sortStatusCodes(Map<Integer, Integer> statusCodesMap) {
        this.popularStatusCodes = statusCodesMap
            .entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .limit(TOP_LEVEL_VALUE)
            .collect(
                Collectors
                    .toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                    )
            );
    }

    private void sortActiveUsers(Map<String, Integer> clientIpmap) {
        this.activeClientsIps = clientIpmap
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(TOP_LEVEL_VALUE)
            .collect(
                Collectors
                    .toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                    )
            );
    }

    private void sortUserAgents(Map<String, Integer> userAgentMap) {
        this.popularUserAgents = userAgentMap
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(TOP_LEVEL_VALUE)
            .collect(
                Collectors
                    .toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                    )
            );
    }
}
