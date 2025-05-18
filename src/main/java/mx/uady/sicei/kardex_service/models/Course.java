package mx.uady.sicei.kardex_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
  private String id;
  private Subject subject;
  private Teacher teacher;
}
