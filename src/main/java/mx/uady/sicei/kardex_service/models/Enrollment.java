package mx.uady.sicei.kardex_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
  private String id;
  private Student student;
  private Course course;
  private CourseType enrollmentType;
}
