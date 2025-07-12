package com.dashboard.api.application.controllers.base;

import java.time.LocalDateTime;

/**
 * A generic class for standard API responses.
 *
 * @param <T> the type of the response data
 */
public class ApiResponse<T> {
  private LocalDateTime timestamp;
  private int status;
  private String message;
  private T data;

  /**
   * Constructs a new {@code ApiResponse} instance.
   *
   * @param status the HTTP status code of the response
   * @param message a descriptive message about the response
   * @param data the response body payload
   */
  public ApiResponse(int status, String message, T data) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
