package mx.uady.sicei.kardex_service.dto.commons;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {
  @Builder.Default private String traceId = UUID.randomUUID().toString();
  private boolean success;
  private String message;
  private T data;
}
