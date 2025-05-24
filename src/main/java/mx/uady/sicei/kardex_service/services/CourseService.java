package mx.uady.sicei.kardex_service.services;

import java.util.Collections;
import java.util.List;
import mx.uady.sicei.kardex_service.dto.course.CreateCourseDTO;
import mx.uady.sicei.kardex_service.dto.course.UpdateCourseDTO;
import mx.uady.sicei.kardex_service.exceptions.ResourceNotFoundException;
import mx.uady.sicei.kardex_service.mappers.CourseMapper;
import mx.uady.sicei.kardex_service.mappers.EnrollmentMapper;
import mx.uady.sicei.kardex_service.models.Course;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.repositories.CourseRepository;
import mx.uady.sicei.kardex_service.repositories.EnrollmentRepository;
import mx.uady.sicei.kardex_service.schemas.CourseSchema;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import mx.uady.sicei.kardex_service.schemas.SubjectSchema;
import mx.uady.sicei.kardex_service.schemas.TeacherSchema;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
  private final CourseMapper courseMapper;
  private final CourseRepository courseRepository;
  private final EnrollmentMapper enrollmentMapper;
  private final EnrollmentRepository enrollmentRepository;
  private final SubjectService subjectService;
  private final TeacherService teacherService;

  public CourseService(
      CourseMapper courseMapper,
      CourseRepository courseRepository,
      EnrollmentMapper enrollmentMapper,
      EnrollmentRepository enrollmentRepository,
      SubjectService subjectService,
      TeacherService teacherService) {
    this.courseMapper = courseMapper;
    this.courseRepository = courseRepository;
    this.enrollmentMapper = enrollmentMapper;
    this.enrollmentRepository = enrollmentRepository;
    this.subjectService = subjectService;
    this.teacherService = teacherService;
  }

  public List<Enrollment> getEnrollmentsByCourseId(String courseId) {
    List<EnrollmentSchema> enrollments = enrollmentRepository.findAllByCourseId(courseId);

    return enrollmentMapper.toModelList(enrollments);
  }

  public List<Course> getAllCourses() {
    List<CourseSchema> courses = courseRepository.findAll();

    return courseMapper.toModelList(courses);
  }

  public Course createCourse(CreateCourseDTO courseRequest) {
    SubjectSchema subject =
        subjectService.findSubjectByIdOrThrowAnException(courseRequest.getSubjectId());

    TeacherSchema teacher =
        teacherService.findTeacherByEmplooyeeIdOrThrowAnException(courseRequest.getTeacherId());

    CourseSchema course =
        courseRepository.save(
            CourseSchema.builder()
                .subject(subject)
                .teacher(teacher)
                .enrollments(Collections.emptyList())
                .build());

    return courseMapper.toModel(course);
  }

  public Course updateCourse(UpdateCourseDTO courseRequest) {
    CourseSchema course = this.findCourseByIdOrThrowAnException(courseRequest.getId());

    SubjectSchema subject =
        subjectService.findSubjectByIdOrThrowAnException(courseRequest.getSubjectId());

    TeacherSchema teacher =
        teacherService.findTeacherByEmplooyeeIdOrThrowAnException(courseRequest.getTeacherId());

    course.setSubject(subject);
    course.setTeacher(teacher);

    return courseMapper.toModel(courseRepository.save(course));
  }

  public void deleteCourse(String courseId) {
    CourseSchema course = this.findCourseByIdOrThrowAnException(courseId);

    courseRepository.delete(course);
  }

  public CourseSchema findCourseByIdOrThrowAnException(String courseId) {
    return courseRepository
        .findById(courseId)
        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
  }
}
