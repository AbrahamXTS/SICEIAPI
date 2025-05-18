package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import java.util.List;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.course.CreateCourseDTO;
import mx.uady.sicei.kardex_service.dto.course.UpdateCourseDTO;
import mx.uady.sicei.kardex_service.models.Course;
import mx.uady.sicei.kardex_service.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@LogController
@RestController
@RequestMapping("/courses")
public class CourseController {
  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<ResponseWrapper<Course>> getCourseById(@PathVariable String courseId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Course>builder()
                .success(true)
                .message("Curso con id %s:".formatted(courseId))
                .data(courseService.getCourseById(courseId))
                .build());
  }

  @GetMapping
  public ResponseEntity<ResponseWrapper<List<Course>>> getAllCourses() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<List<Course>>builder()
                .success(true)
                .message("Todos los cursos:")
                .data(courseService.getAllCourses())
                .build());
  }

  @PostMapping
  public ResponseEntity<ResponseWrapper<Course>> createCourse(
      @RequestBody @Valid CreateCourseDTO course) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseWrapper.<Course>builder()
                .success(true)
                .message("Curso creado.")
                .data(courseService.createCourse(course))
                .build());
  }

  @PutMapping
  public ResponseEntity<ResponseWrapper<Course>> updateCourse(
      @RequestBody @Valid UpdateCourseDTO course) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Course>builder()
                .success(true)
                .message("Curso %s actualizado.".formatted(course.getId()))
                .data(courseService.updateCourse(course))
                .build());
  }

  @DeleteMapping("/{courseId}")
  public ResponseEntity<ResponseWrapper<Void>> deleteCourseById(@PathVariable String courseId) {
    courseService.deleteCourse(courseId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(true)
                .message("Curso %s eliminado.".formatted(courseId))
                .build());
  }
}
