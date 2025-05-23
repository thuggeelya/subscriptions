package ru.thuggeelya.subscriptions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }
}
