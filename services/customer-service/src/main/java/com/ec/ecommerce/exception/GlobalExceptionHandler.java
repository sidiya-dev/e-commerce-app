package com.ec.ecommerce.exception;

import com.ec.ecommerce.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    String simpleField = field.contains(".")
                            ? field.substring(field.lastIndexOf('.') + 1)
                            : field;
                    errors.put(simpleField, Objects.requireNonNull(error.getDefaultMessage()));
                });

        log.warn("Validation failed on {} — {}", request.getRequestURI(), errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Validation failed",
                        request.getRequestURI(),
                        errors));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, HttpServletRequest request) {
        log.warn("Email conflict on {} - {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT,
                        exception.getMessage(),
                        request.getRequestURI(),
                        Map.of()));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException exception, HttpServletRequest request) {
        log.warn("Customer not found on {} - {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND,
                        "Customer not found",
                        request.getRequestURI(),
                        Map.of()));
    }
}
