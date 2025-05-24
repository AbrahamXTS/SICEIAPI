package mx.uady.sicei.kardex_service.services;

import java.util.Collections;
import java.util.List;
import mx.uady.sicei.kardex_service.dto.student.CreateStudentDTO;
import mx.uady.sicei.kardex_service.dto.student.UpdateStudentDTO;
import mx.uady.sicei.kardex_service.exceptions.ResourceNotFoundException;
import mx.uady.sicei.kardex_service.mappers.EnrollmentMapper;
import mx.uady.sicei.kardex_service.mappers.StudentMapper;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.models.Student;
import mx.uady.sicei.kardex_service.repositories.EnrollmentRepository;
import mx.uady.sicei.kardex_service.repositories.StudentRepository;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import mx.uady.sicei.kardex_service.schemas.StudentSchema;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
  private final EnrollmentRepository enrollmentRepository;
  private final EnrollmentMapper enrollmentMapper;
  private final StudentMapper studentMapper;
  private final StudentRepository studentRepository;

  public StudentService(
      EnrollmentRepository enrollmentRepository,
      EnrollmentMapper enrollmentMapper,
      StudentMapper studentMapper,
      StudentRepository studentRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.enrollmentMapper = enrollmentMapper;
    this.studentMapper = studentMapper;
    this.studentRepository = studentRepository;
  }

  public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
    List<EnrollmentSchema> enrollments = enrollmentRepository.findAllByStudentId(studentId);

    return enrollmentMapper.toModelList(enrollments);
  }

  public List<Student> getAllStudents() {
    List<StudentSchema> students = studentRepository.findAll();

    return studentMapper.toModelList(students);
  }

  public Student createStudent(CreateStudentDTO studentRequest) {
    StudentSchema student =
        studentRepository.save(
            StudentSchema.builder()
                .name(studentRequest.getName())
                .lastName(studentRequest.getLastName())
                .secondLastName(studentRequest.getSecondLastName())
                .email(studentRequest.getEmail())
                .equivalentDegree(studentRequest.getEquivalentDegree())
                .enrollments(Collections.emptyList())
                .build());

    return studentMapper.toModel(student);
  }

  public Student updateStudent(UpdateStudentDTO studentRequest) {
    StudentSchema student = this.findStudentByIdOrThrowAnException(studentRequest.getId());

    student.setName(studentRequest.getName());
    student.setLastName(studentRequest.getLastName());
    student.setSecondLastName(studentRequest.getSecondLastName());
    student.setEmail(studentRequest.getEmail());
    student.setEquivalentDegree(studentRequest.getEquivalentDegree());

    return studentMapper.toModel(studentRepository.save(student));
  }

  public void deleteStudent(String studentId) {
    StudentSchema student = this.findStudentByIdOrThrowAnException(studentId);

    studentRepository.delete(student);
  }

  public StudentSchema findStudentByIdOrThrowAnException(String studentId) {
    return studentRepository
        .findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
  }
}
