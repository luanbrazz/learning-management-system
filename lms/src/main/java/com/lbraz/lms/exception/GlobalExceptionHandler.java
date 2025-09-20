package com.lbraz.lms.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    String messageKey = error.getDefaultMessage();
                    return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
                })
                .collect(Collectors.joining("; "));

        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(BAD_REQUEST.value())
                .error("Validation Error")
                .message(errorMessage)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    private String getErrorMessage(FieldError fieldError) {
        String message = fieldError.getDefaultMessage();
        String field = fieldError.getField();

        // Tenta obter a mensagem traduzida do MessageSource
        try {
            String messageKey = fieldError.getCodes()[0];
            return messageSource.getMessage(messageKey, null, fieldError.getDefaultMessage(), null);
        } catch (Exception e) {
            return String.format("%s: %s", field, message);
        }
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<StandardError> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest request) {
        logger.warn("Duplicate resource error: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(CONFLICT.value())
                .error(CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(CONFLICT).body(error);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<StandardError> handleDuplicateUsernameException(DuplicateUsernameException ex, HttpServletRequest request) {
        logger.warn("Duplicate username error: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(CONFLICT.value())
                .error(CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidAgeException.class)
    public ResponseEntity<StandardError> handleInvalidAgeException(InvalidAgeException ex, HttpServletRequest request) {
        logger.warn("Invalid age error: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(UNPROCESSABLE_ENTITY.value())
                .error(UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.warn("Resource not found: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<StandardError> handleInvalidTimeException(InvalidTimeException ex, HttpServletRequest request) {
        logger.warn("Invalid time error: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(UNPROCESSABLE_ENTITY.value())
                .error(UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(InvalidStatusChangeException.class)
    public ResponseEntity<StandardError> handleInvalidStatusChangeException(InvalidStatusChangeException ex, HttpServletRequest request) {
        logger.warn("Invalid status change error: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(UNPROCESSABLE_ENTITY.value())
                .error(UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        logger.warn("User not found: {}", ex.getMessage());
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(error);
    }
}