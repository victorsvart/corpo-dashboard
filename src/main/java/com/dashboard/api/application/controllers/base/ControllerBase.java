package com.dashboard.api.application.controllers.base;

import jakarta.annotation.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Global response wrapper for REST controllers.
 *
 * <p>This class intercepts all responses returned by controller methods (except those already
 * wrapped in {@link ApiResponse}) and wraps them in a standard {@link ApiResponse} structure for
 * consistent response formatting.
 */
@RestControllerAdvice
public class ControllerBase implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(
      @NonNull MethodParameter returnType,
      @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    if (returnType.getParameterType().equals(ApiResponse.class)) {
      return false;
    }

    return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
  }

  @SuppressWarnings("null")
  @Override
  public Object beforeBodyWrite(
      @Nullable Object body,
      @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType,
      @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response) {

    if (body instanceof ApiResponse) {
      return body;
    }

    int rawStatus = 200;
    String statusHeader = response.getHeaders().getFirst("X-Status");
    if (statusHeader != null) {
      try {
        rawStatus = Integer.parseInt(statusHeader);
      } catch (NumberFormatException ignored) {
        throw ignored;
      }
    }

    HttpStatus status = HttpStatus.resolve(rawStatus);
    if (status == null) {
      status = HttpStatus.OK;
    }

    return new ApiResponse<>(status.value(), "Success", body);
  }
}
