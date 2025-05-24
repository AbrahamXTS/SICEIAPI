package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import java.util.List;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.teacher.CreateTeacherDTO;
import mx.uady.sicei.kardex_service.dto.teacher.UpdateTeacherDTO;
import mx.uady.sicei.kardex_service.models.Teacher;
import mx.uady.sicei.kardex_service.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/teachers")
public class TeacherController {
  private final TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('teacher:list')")
  public ResponseEntity<ResponseWrapper<List<Teacher>>> getAllTeachers() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<List<Teacher>>builder()
                .success(true)
                .message("Todos los profesores:")
                .data(teacherService.getAllTeachers())
                .build());
  }

  @PostMapping
  @PreAuthorize("hasAuthority('teacher:create')")
  public ResponseEntity<ResponseWrapper<Teacher>> createTeacher(
      @RequestBody @Valid CreateTeacherDTO teacher) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseWrapper.<Teacher>builder()
                .success(true)
                .message(
                    "Profesor %s %s %s creado."
                        .formatted(
                            teacher.getName(), teacher.getLastName(), teacher.getSecondLastName()))
                .data(teacherService.createTeacher(teacher))
                .build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('teacher:update')")
  public ResponseEntity<ResponseWrapper<Teacher>> updateTeacher(
      @RequestBody @Valid UpdateTeacherDTO teacher) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Teacher>builder()
                .success(true)
                .message("Profesor %s actualizado.".formatted(teacher.getEmployeeId()))
                .data(teacherService.updateTeacher(teacher))
                .build());
  }

  @DeleteMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('teacher:delete')")
  public ResponseEntity<ResponseWrapper<Void>> deleteTeacherByEmployeeId(
      @PathVariable String employeeId) {
    teacherService.deleteTeacher(employeeId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(true)
                .message("Profesor %s eliminado.".formatted(employeeId))
                .build());
  }
}
