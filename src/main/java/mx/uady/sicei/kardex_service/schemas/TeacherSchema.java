package mx.uady.sicei.kardex_service.schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "teacher")
public class TeacherSchema extends BaseSchema {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String employeeId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String lastName;

  @Column private String secondLastName;

  @Column(nullable = false, unique = true)
  private String email;

  @OneToMany(mappedBy = "teacher")
  private List<CourseSchema> coursesTaught;
}
