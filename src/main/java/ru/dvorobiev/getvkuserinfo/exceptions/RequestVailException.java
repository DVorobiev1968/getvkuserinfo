package ru.dvorobiev.getvkuserinfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestVailException extends RuntimeException {
    public RequestVailException(String message) {
        super(message);
    }
}
