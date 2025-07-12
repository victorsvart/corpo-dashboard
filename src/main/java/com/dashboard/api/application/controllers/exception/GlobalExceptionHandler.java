package com.dashboard.api.application.controllers.exception;

import com.dashboard.api.application.controllers.base.ApiResponse;
import com.dashboard.api.domain.exception.UnauthorizedException;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler that intercepts exceptions thrown by controller methods and translates
 * them into standardized HTTP responses with appropriate status codes and body payloads
 * encapsulated in {@link ApiResponse}.
 *
 * <p>This class uses Spring's {@link RestControllerAdvice} to apply exception handling globally
 * across all REST controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<ApiResponse<Object>> handleEntityExists(EntityExistsException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiResponse<Object>> handleUnauthorized(Exception ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleAllOtherExceptions(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null));
  }
}
