package valter.gabriel.Easy.Manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ApiRequestException extends HttpClientErrorException {

    public ApiRequestException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }


}
