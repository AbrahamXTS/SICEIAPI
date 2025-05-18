package mx.uady.sicei.kardex_service.services;

import java.util.List;
import mx.uady.sicei.kardex_service.dto.enrollment.CreateEnrollmentDTO;
import mx.uady.sicei.kardex_service.dto.enrollment.UpdateEnrollmentDTO;
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

  public Enrollment getEnrollmentById(String enrollmentId) {
    EnrollmentSchema enrollment = this.findEnrollmentByIdOrThrowAnException(enrollmentId);

    return enrollmentMapper.toModel(enrollment);
  }

  public List<Enrollment> getAllEnrollments() {
    List<EnrollmentSchema> enrollments = enrollmentRepository.findAll();

    return enrollmentMapper.toModelList(enrollments);
  }

  public Enrollment createEnrollment(CreateEnrollmentDTO enrollmentRequest) {
    CourseSchema course =
        courseService.findCourseByIdOrThrowAnException(enrollmentRequest.getCourseId());

    StudentSchema student =
        studentService.findStudentByIdOrThrowAnException(enrollmentRequest.getStudentId());

    EnrollmentSchema enrollment =
        enrollmentRepository.save(
            EnrollmentSchema.builder()
                .course(course)
                .student(student)
                .enrollmentType(enrollmentRequest.getEnrollmentType())
                .build());

    return enrollmentMapper.toModel(enrollment);
  }

  public Enrollment updateEnrollment(UpdateEnrollmentDTO enrollmentRequest) {
    EnrollmentSchema enrollment =
        this.findEnrollmentByIdOrThrowAnException(enrollmentRequest.getId());

    CourseSchema course =
        courseService.findCourseByIdOrThrowAnException(enrollmentRequest.getCourseId());

    StudentSchema student =
        studentService.findStudentByIdOrThrowAnException(enrollmentRequest.getStudentId());

    enrollment.setCourse(course);
    enrollment.setStudent(student);
    enrollment.setEnrollmentType(enrollmentRequest.getEnrollmentType());

    return enrollmentMapper.toModel(enrollmentRepository.save(enrollment));
  }

  public void deleteEnrollment(String enrollmentId) {
    EnrollmentSchema enrollment = this.findEnrollmentByIdOrThrowAnException(enrollmentId);

    enrollmentRepository.delete(enrollment);
  }

  public EnrollmentSchema findEnrollmentByIdOrThrowAnException(String enrollmentId) {
    return enrollmentRepository
        .findById(enrollmentId)
        .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
  }
}
