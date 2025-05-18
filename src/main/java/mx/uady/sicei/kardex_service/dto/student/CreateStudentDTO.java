package mx.uady.sicei.kardex_service.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDTO {
  @NotBlank private String name;

  @NotBlank private String lastName;

  private String secondLastName;

  @Email @NotBlank private String email;

  @Positive @NotNull private Integer equivalentDegree;
}
