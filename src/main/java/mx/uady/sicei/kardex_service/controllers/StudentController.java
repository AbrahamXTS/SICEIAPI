package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import java.util.List;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.student.CreateStudentDTO;
import mx.uady.sicei.kardex_service.dto.student.UpdateStudentDTO;
import mx.uady.sicei.kardex_service.models.Student;
import mx.uady.sicei.kardex_service.services.StudentService;
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
@RequestMapping("/students")
public class StudentController {
  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/{studentId}")
  public ResponseEntity<ResponseWrapper<Student>> getStudentById(@PathVariable String studentId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Student>builder()
                .success(true)
                .message("Estudiante con id %s:".formatted(studentId))
                .data(studentService.getStudentById(studentId))
                .build());
  }

  @GetMapping
  public ResponseEntity<ResponseWrapper<List<Student>>> getAllStudents() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<List<Student>>builder()
                .success(true)
                .message("Todos los estudiantes:")
                .data(studentService.getAllStudents())
                .build());
  }

  @PostMapping
  public ResponseEntity<ResponseWrapper<Student>> createStudent(
      @RequestBody @Valid CreateStudentDTO student) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseWrapper.<Student>builder()
                .success(true)
                .message(
                    "Estudiante %s %s %s creado."
                        .formatted(
                            student.getName(), student.getLastName(), student.getSecondLastName()))
                .data(studentService.createStudent(student))
                .build());
  }

  @PutMapping
  public ResponseEntity<ResponseWrapper<Student>> updateStudent(
      @RequestBody @Valid UpdateStudentDTO student) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Student>builder()
                .success(true)
                .message("Estudiante %s actualizado.".formatted(student.getId()))
                .data(studentService.updateStudent(student))
                .build());
  }

  @DeleteMapping("/{studentId}")
  public ResponseEntity<ResponseWrapper<Void>> deleteStudentById(@PathVariable String studentId) {
    studentService.deleteStudent(studentId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(true)
                .message("Estudiante %s eliminado.".formatted(studentId))
                .build());
  }
}
