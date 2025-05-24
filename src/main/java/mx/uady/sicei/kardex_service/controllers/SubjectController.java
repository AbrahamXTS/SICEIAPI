package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import java.util.List;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.subject.CreateSubjectDTO;
import mx.uady.sicei.kardex_service.dto.subject.UpdateSubjectDTO;
import mx.uady.sicei.kardex_service.models.Subject;
import mx.uady.sicei.kardex_service.services.SubjectService;
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
@RequestMapping("/subjects")
public class SubjectController {
  private final SubjectService subjectService;

  public SubjectController(SubjectService subjectService) {
    this.subjectService = subjectService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('subject:list')")
  public ResponseEntity<ResponseWrapper<List<Subject>>> getAllSubjects() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<List<Subject>>builder()
                .success(true)
                .message("Todas las asignaturas:")
                .data(subjectService.getAllSubjects())
                .build());
  }

  @PostMapping
  @PreAuthorize("hasAuthority('subject:create')")
  public ResponseEntity<ResponseWrapper<Subject>> createSubject(
      @RequestBody @Valid CreateSubjectDTO subject) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseWrapper.<Subject>builder()
                .success(true)
                .message("Asignatura '%s' creada.".formatted(subject.getName()))
                .data(subjectService.createSubject(subject))
                .build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('subject:update')")
  public ResponseEntity<ResponseWrapper<Subject>> updateSubject(
      @RequestBody @Valid UpdateSubjectDTO subject) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Subject>builder()
                .success(true)
                .message("Asignatura %s actualizada.".formatted(subject.getId()))
                .data(subjectService.updateSubject(subject))
                .build());
  }

  @DeleteMapping("/{subjectId}")
  @PreAuthorize("hasAuthority('subject:delete')")
  public ResponseEntity<ResponseWrapper<Void>> deleteSubjectById(@PathVariable String subjectId) {
    subjectService.deleteSubject(subjectId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(true)
                .message("Asignatura %s eliminada.".formatted(subjectId))
                .build());
  }
}
