package com.linksphere.common.exception;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.response.ApiError;
import com.linksphere.common.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException ex) {
        return buildResponse(
            ex.getErrorCode(),
            ex.getMessage(),
            null
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return buildResponse(
            ErrorCode.INVALID_CREDENTIALS,
            ErrorCode.INVALID_CREDENTIALS.getMessage(),
            null
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return buildResponse(
            ErrorCode.INVALID_CREDENTIALS,
            ErrorCode.INVALID_CREDENTIALS.getMessage(),
            null
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildResponse(
            ErrorCode.ACCESS_DENIED,
            ex.getMessage(),
            null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(
            ErrorCode.VALIDATION_FAILED,
            errors,
            null
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(
            ErrorCode.VALIDATION_FAILED,
            errors,
            null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        return buildResponse(
            ErrorCode.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            null
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            ErrorCode errorCode,
            String message,
            List<ApiError> errors
    ) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .status(errorCode.getHttpStatus().value())
                .errorCode(errorCode.getCode())
                .message(message)
                .errors(errors)
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
}