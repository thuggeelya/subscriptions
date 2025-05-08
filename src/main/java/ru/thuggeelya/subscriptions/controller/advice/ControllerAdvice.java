package ru.thuggeelya.subscriptions.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.thuggeelya.subscriptions.dto.response.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        ex.getBindingResult()
                          .getFieldErrors()
                          .stream()
                          .map(FieldError::getDefaultMessage)
                          .toList()
                )
        );
    }
}
