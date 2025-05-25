package mx.uady.sicei.kardex_service.controllers;

import java.util.List;
import java.util.Map;
import mx.uady.sicei.kardex_service.aspects.LogController;
import mx.uady.sicei.kardex_service.dto.commons.ResponseWrapper;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.services.MetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@LogController
@RestController
@RequestMapping("/metrics")
public class MetricsController {
  private final MetricsService metricsService;

  public MetricsController(MetricsService metricsService) {
    this.metricsService = metricsService;
  }

  @GetMapping("/students/approved-by-grade")
  public ResponseEntity<ResponseWrapper<Map<Integer, Map<String, List<Enrollment>>>>>
      getRegularStudentsGroupedByEquivalentDegree() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Map<Integer, Map<String, List<Enrollment>>>>builder()
                .success(true)
                .message("Todos los grados con sus alumnos aprobados:")
                .data(metricsService.getRegularStudentsGroupedByEquivalentDegree())
                .build());
  }

  @GetMapping("/students/with-failures-by-grade")
  public ResponseEntity<ResponseWrapper<Map<Integer, Map<String, List<Enrollment>>>>>
      getIrregularStudentsGroupedByEquivalentDegree() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseWrapper.<Map<Integer, Map<String, List<Enrollment>>>>builder()
                .success(true)
                .message("Todos los grados con sus alumnos con al menos una materia reprobada:")
                .data(metricsService.getIrregularStudentsGroupedByEquivalentDegree())
                .build());
  }
}
