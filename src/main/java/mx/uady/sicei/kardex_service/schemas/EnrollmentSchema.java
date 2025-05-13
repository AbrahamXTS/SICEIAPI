package mx.uady.sicei.kardex_service.schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import mx.uady.sicei.kardex_service.models.CourseType;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "enrollment")
public class EnrollmentSchema extends BaseSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private StudentSchema student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private CourseSchema course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType enrollmentType;
}
