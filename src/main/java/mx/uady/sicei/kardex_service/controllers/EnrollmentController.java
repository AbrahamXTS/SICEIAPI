package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.enrollment.CreateEnrollmentDTO;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.services.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping
  @PreAuthorize("hasAuthority('enrollment:create')")
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

  @DeleteMapping("/{enrollmentId}")
  @PreAuthorize("hasAuthority('enrollment:delete')")
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
