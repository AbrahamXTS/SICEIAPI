package mx.uady.sicei.kardex_service.dto.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeacherDTO {
  @NotBlank private String employeeId;

  @NotBlank private String name;

  @NotBlank private String lastName;

  private String secondLastName;

  @Email @NotBlank private String email;
}
