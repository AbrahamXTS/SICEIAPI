package mx.uady.sicei.kardex_service.services;

import java.util.Objects;
import mx.uady.sicei.kardex_service.dto.enrollment.CreateEnrollmentDTO;
import mx.uady.sicei.kardex_service.exceptions.ConflictWithExistingResourceException;
import mx.uady.sicei.kardex_service.exceptions.ResourceNotFoundException;
import mx.uady.sicei.kardex_service.mappers.EnrollmentMapper;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.repositories.EnrollmentRepository;
import mx.uady.sicei.kardex_service.schemas.CourseSchema;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import mx.uady.sicei.kardex_service.schemas.StudentSchema;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
  private final EnrollmentMapper enrollmentMapper;
  private final EnrollmentRepository enrollmentRepository;
  private final CourseService courseService;
  private final StudentService studentService;

  public EnrollmentService(
      EnrollmentMapper enrollmentMapper,
      EnrollmentRepository enrollmentRepository,
      CourseService courseService,
      StudentService studentService) {
    this.enrollmentMapper = enrollmentMapper;
    this.enrollmentRepository = enrollmentRepository;
    this.courseService = courseService;
    this.studentService = studentService;
  }

  public Enrollment createEnrollment(CreateEnrollmentDTO enrollmentRequest) {
    CourseSchema course =
        courseService.findCourseByIdOrThrowAnException(enrollmentRequest.getCourseId());

    StudentSchema student =
        studentService.findStudentByIdOrThrowAnException(enrollmentRequest.getStudentId());

    if (!Objects.equals(course.getSubject().getOfferedInDegree(), student.getEquivalentDegree())) {
      throw new ConflictWithExistingResourceException(
          "Los estudiantes solo pueden cursar asignaturas que se impartan para su mismo grado"
              + " académico.");
    }

    EnrollmentSchema enrollment =
        enrollmentRepository.save(
            EnrollmentSchema.builder()
                .course(course)
                .student(student)
                .enrollmentType(enrollmentRequest.getEnrollmentType())
                .build());

    return enrollmentMapper.toModel(enrollment);
  }

  public void deleteEnrollment(String enrollmentId) {
    EnrollmentSchema enrollment = this.findEnrollmentByIdOrThrowAnException(enrollmentId);

    enrollmentRepository.delete(enrollment);
  }

  public EnrollmentSchema findEnrollmentByIdOrThrowAnException(String enrollmentId) {
    return enrollmentRepository
        .findById(enrollmentId)
        .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada."));
  }
}
