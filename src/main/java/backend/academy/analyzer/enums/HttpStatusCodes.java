package backend.academy.analyzer.enums;

import lombok.Getter;

@Getter
public enum HttpStatusCodes {

    CONTINUE(100, "Continue"),

    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    OK(200, "OK"),

    CREATED(201, "Created"),

    ACCEPTED(202, "Accepted"),

    MULTIPLE_CHOICES(300, "Multiple Choices"),

    MOVED_PERMANENTLY(301, "Moved Permanently"),

    FOUND(302, "Found"),

    BAD_REQUEST(400, "Bad Request"),

    UNAUTHORIZED(401, "Unauthorized"),

    FORBIDDEN(403, "Forbidden"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    NOT_IMPLEMENTED(501, "Not Implemented"),

    BAD_GATEWAY(502, "Bad Gateway"),

    UNKNOWN(-1, "Unknown Status");

    private final int code;

    private final String description;

    HttpStatusCodes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static HttpStatusCodes getStatusFromCode(int code) {
        for (HttpStatusCodes status : HttpStatusCodes.values()) {
            if (status.code() == code) {
                return status;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
