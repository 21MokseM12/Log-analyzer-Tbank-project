package backend.academy.analyzer.model;

import java.time.LocalDateTime;

public record Log(String remoteAddr, String remoteUser, LocalDateTime timeLocal,
                  String request, int httpStatus, int responseSize,
                  String httpReferer, String httpUserAgent) {
}
