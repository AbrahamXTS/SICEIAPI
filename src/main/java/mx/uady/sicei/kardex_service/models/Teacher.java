package mx.uady.sicei.kardex_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
  private String employeeId;
  private String name;
  private String lastName;
  private String secondLastName;
  private String email;
}
