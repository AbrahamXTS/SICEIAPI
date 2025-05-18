package mx.uady.sicei.kardex_service.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDTO {
  @NotBlank private String subjectId;
  @NotBlank private String teacherId;
}
