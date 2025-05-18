package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import java.util.List;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.enrollment.CreateEnrollmentDTO;
import mx.uady.sicei.kardex_service.dto.enrollment.UpdateEnrollmentDTO;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.services.EnrollmentService;
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
@RequestMapping("/enrollments")
public class EnrollmentController {
  private final EnrollmentService enrollmentService;

  public EnrollmentController(EnrollmentService enrollmentService) {
    this.enrollmentService = enrollmentService;
  }

  @GetMapping("/{enrollmentId}")
  public ResponseEntity<ResponseWrapper<Enrollment>> getEnrollmentById(
      @PathVariable String enrollmentId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Enrollment>builder()
                .success(true)
                .message("Inscripción con id %s:".formatted(enrollmentId))
                .data(enrollmentService.getEnrollmentById(enrollmentId))
                .build());
  }

  @GetMapping
  public ResponseEntity<ResponseWrapper<List<Enrollment>>> getAllEnrollments() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<List<Enrollment>>builder()
                .success(true)
                .message("Todas las inscripciones:")
                .data(enrollmentService.getAllEnrollments())
                .build());
  }

  @PostMapping
  public ResponseEntity<ResponseWrapper<Enrollment>> createEnrollment(
      @RequestBody @Valid CreateEnrollmentDTO enrollment) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseWrapper.<Enrollment>builder()
                .success(true)
                .message("Inscripción realizada.")
                .data(enrollmentService.createEnrollment(enrollment))
                .build());
  }

  @PutMapping
  public ResponseEntity<ResponseWrapper<Enrollment>> updateEnrollment(
      @RequestBody @Valid UpdateEnrollmentDTO enrollment) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Enrollment>builder()
                .success(true)
                .message("Inscripción %s actualizada.".formatted(enrollment.getId()))
                .data(enrollmentService.updateEnrollment(enrollment))
                .build());
  }

  @DeleteMapping("/{enrollmentId}")
  public ResponseEntity<ResponseWrapper<Void>> deleteEnrollmentById(
      @PathVariable String enrollmentId) {
    enrollmentService.deleteEnrollment(enrollmentId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(true)
                .message("Inscripción %s eliminada.".formatted(enrollmentId))
                .build());
  }
}
