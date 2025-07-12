package com.dashboard.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown to indicate that a request has been denied due to unauthorized access.
 *
 * <p>This exception is annotated with {@link ResponseStatus} to automatically return a {@link
 * HttpStatus#UNAUTHORIZED} (401) HTTP response when thrown in a Spring MVC controller.
 *
 * <p>Typical usage includes scenarios where authentication is missing, invalid, or insufficient for
 * accessing a specific resource.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED) // Sends 401 response
public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException() {
    super("Unauthorized access");
  }

  public UnauthorizedException(String message) {
    super(message);
  }

  public UnauthorizedException(String message, Throwable cause) {
    super(message, cause);
  }
}
