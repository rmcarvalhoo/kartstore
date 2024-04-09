package br.com.kartstore.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
    List<String> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(violation.getRootBeanClass().getName() + " " +
          violation.getPropertyPath() + ": " + violation.getMessage());
    }

    ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ResponseEntity<Object> onIllegalStateException(
      MethodArgumentTypeMismatchException e) {
    String str = "";
    if (e.getRequiredType() != null) {
      str = e.getRequiredType().getName();
    }
    String error = e.getName() + " should be of type " + str;
    ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler(ResourceAccessException.class)
  ResponseEntity<Object> onResourceAccessException(
      ResourceAccessException e) {
    String error = "Internal server error";
    ApiError apiError =
        new ApiError(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler(RuntimeException.class)
  ResponseEntity<Object> onRuntimeException(
      RuntimeException e) {
    String error = e.getMessage();
    ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Object> onMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    StringBuffer errors = new StringBuffer();
    if (e != null && e.getBindingResult()  != null) {
      e.getBindingResult().getAllErrors().forEach((error) -> {
        errors.append(error.getDefaultMessage());
        errors.append("; ");
      });
    }

    ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), errors.toString());
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

}
