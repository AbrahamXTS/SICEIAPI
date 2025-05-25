package mx.uady.sicei.kardex_service.exceptions;

import java.util.stream.Collectors;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
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

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ResponseWrapper<Void>> authorizationDeniedExceptionHandler(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ResponseWrapper.<Void>builder()
                .success(false)
                .message(
                    "Lo sentimos, pero no tienes los permisos suficientes para acceder a este"
                        + " recurso.")
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

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResponseWrapper<Void>> dataIntegrityViolationExceptionHandler(
      DataIntegrityViolationException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(false)
                .message(
                    "No se pudo completar la operación debido a un conflicto con los datos"
                        + " existentes.")
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
