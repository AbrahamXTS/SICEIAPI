package mx.uady.sicei.kardex_service.dto.enrollment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.uady.sicei.kardex_service.models.CourseType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnrollmentDTO {
  @NotBlank private String id;
  @NotBlank private String studentId;
  @NotBlank private String courseId;
  private CourseType enrollmentType;
}
