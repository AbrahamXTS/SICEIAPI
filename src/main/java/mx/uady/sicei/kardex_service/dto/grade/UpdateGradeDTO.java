package mx.uady.sicei.kardex_service.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGradeDTO {
  @NotBlank private String id;
  @NotBlank private String enrollmentId;

  @Min(0)
  @Max(100)
  private Double score;
}
