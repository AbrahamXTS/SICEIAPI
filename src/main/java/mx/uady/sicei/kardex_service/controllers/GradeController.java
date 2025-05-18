package mx.uady.sicei.kardex_service.controllers;

import jakarta.validation.Valid;
import java.util.List;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.dto.grade.CreateGradeDTO;
import mx.uady.sicei.kardex_service.dto.grade.UpdateGradeDTO;
import mx.uady.sicei.kardex_service.models.Grade;
import mx.uady.sicei.kardex_service.services.GradeService;
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
@RequestMapping("/grades")
public class GradeController {
  private final GradeService gradeService;

  public GradeController(GradeService gradeService) {
    this.gradeService = gradeService;
  }

  @GetMapping("/{gradeId}")
  public ResponseEntity<ResponseWrapper<Grade>> getGradeById(@PathVariable String gradeId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Grade>builder()
                .success(true)
                .message("Calificación con id %s:".formatted(gradeId))
                .data(gradeService.getGradeById(gradeId))
                .build());
  }

  @GetMapping
  public ResponseEntity<ResponseWrapper<List<Grade>>> getAllGrades() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<List<Grade>>builder()
                .success(true)
                .message("Todas las calificaciones:")
                .data(gradeService.getAllGrades())
                .build());
  }

  @PostMapping
  public ResponseEntity<ResponseWrapper<Grade>> createGrade(
      @RequestBody @Valid CreateGradeDTO grade) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseWrapper.<Grade>builder()
                .success(true)
                .message("Calificación asignada.")
                .data(gradeService.createGrade(grade))
                .build());
  }

  @PutMapping
  public ResponseEntity<ResponseWrapper<Grade>> updateGrade(
      @RequestBody @Valid UpdateGradeDTO grade) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Grade>builder()
                .success(true)
                .message("Calificación %s actualizada.".formatted(grade.getId()))
                .data(gradeService.updateGrade(grade))
                .build());
  }

  @DeleteMapping("/{gradeId}")
  public ResponseEntity<ResponseWrapper<Void>> deleteGradeById(@PathVariable String gradeId) {
    gradeService.deleteGrade(gradeId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            ResponseWrapper.<Void>builder()
                .success(true)
                .message("Calificación %s eliminada.".formatted(gradeId))
                .build());
  }
}
