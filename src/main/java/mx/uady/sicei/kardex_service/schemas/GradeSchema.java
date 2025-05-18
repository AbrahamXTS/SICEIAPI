package mx.uady.sicei.kardex_service.schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Entity(name = "grade")
public class GradeSchema extends BaseSchema {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column
  @Min(0)
  @Max(100)
  private Double score;

  @OneToOne(optional = false)
  @JoinColumn(name = "enrollment_id", unique = true)
  private EnrollmentSchema enrollment;
}
