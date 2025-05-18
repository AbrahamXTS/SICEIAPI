package mx.uady.sicei.kardex_service.exceptions;

public class ConflictWithExistingResourceException extends RuntimeException {
  public ConflictWithExistingResourceException(String message) {
    super(message);
  }
}
