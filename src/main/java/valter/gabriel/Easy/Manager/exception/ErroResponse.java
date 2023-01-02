package valter.gabriel.Easy.Manager.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErroResponse {
    private final String message;
    private final int code;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ErroResponse(String message, int code, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
