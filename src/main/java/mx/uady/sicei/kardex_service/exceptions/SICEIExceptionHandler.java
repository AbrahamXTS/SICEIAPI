package mx.uady.sicei.kardex_service.exceptions;

import java.util.stream.Collectors;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SICEIExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ResponseWrapper<Void>> resourceNotFoundExceptionHandler(
      ResourceNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ResponseWrapper.<Void>builder()
                .success(false)
                .message(exception.getMessage())
                .data(null)
                .build());
  }

  @ExceptionHandler(ConflictWithExistingResourceException.class)
  public ResponseEntity<ResponseWrapper<Void>> conflictWithExistingResourceExceptionHandler(
      ConflictWithExistingResourceException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ResponseWrapper.<Void>builder()
                .success(false)
                .message(exception.getMessage())
                .data(null)
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseWrapper<Void>> validationExceptionHandler(
      MethodArgumentNotValidException exception) {
    String errors =
        exception.getBindingResult().getAllErrors().stream()
            .map(error -> ((FieldError) error).getField() + " " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ResponseWrapper.<Void>builder()
                .success(false)
                .message("Algunas validaciones fallaron: %s".formatted(errors))
                .data(null)
                .build());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ResponseWrapper<Void>> generalExceptionHandler(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ResponseWrapper.<Void>builder()
                .success(false)
                .message("Error desconocido.")
                .data(null)
                .build());
  }
}
