package mx.uady.sicei.kardex_service.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.uady.sicei.kardex_service.mappers.EnrollmentMapper;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.repositories.EnrollmentRepository;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
  private final EnrollmentMapper enrollmentMapper;
  private final EnrollmentRepository enrollmentRepository;

  public MetricsService(
      EnrollmentMapper enrollmentMapper, EnrollmentRepository enrollmentRepository) {
    this.enrollmentMapper = enrollmentMapper;
    this.enrollmentRepository = enrollmentRepository;
  }

  public Map<Integer, Map<String, List<Enrollment>>> getRegularStudentsGroupedByEquivalentDegree() {
    Map<Integer, Map<String, List<Enrollment>>> regularStudentsGroupedByEquivalentDegree =
        new HashMap<>();

    List<EnrollmentSchema> enrollmentsWithPassingGrades =
        enrollmentRepository.findEnrollmentsForStudentsWithOnlyPassingGrades();

    for (EnrollmentSchema enrollment : enrollmentsWithPassingGrades) {
      Integer equivalentDegree = enrollment.getStudent().getEquivalentDegree();
      String studentId = enrollment.getStudent().getId();
      Enrollment enrollmentModel = enrollmentMapper.toModel(enrollment);

      regularStudentsGroupedByEquivalentDegree
          .computeIfAbsent(equivalentDegree, key -> new HashMap<>())
          .computeIfAbsent(studentId, key -> new ArrayList<>())
          .add(enrollmentModel);
    }

    return regularStudentsGroupedByEquivalentDegree;
  }

  public Map<Integer, Map<String, List<Enrollment>>>
      getIrregularStudentsGroupedByEquivalentDegree() {
    Map<Integer, Map<String, List<Enrollment>>> irregularStudentsGroupedByEquivalentDegree =
        new HashMap<>();

    List<EnrollmentSchema> enrollmentsWithFailingGrades =
        enrollmentRepository.findEnrollmentsForStudentsWithFailingGrades();

    for (EnrollmentSchema enrollment : enrollmentsWithFailingGrades) {
      Integer equivalentDegree = enrollment.getStudent().getEquivalentDegree();
      String studentId = enrollment.getStudent().getId();
      Enrollment enrollmentModel = enrollmentMapper.toModel(enrollment);

      irregularStudentsGroupedByEquivalentDegree
          .computeIfAbsent(equivalentDegree, k -> new HashMap<>())
          .computeIfAbsent(studentId, k -> new ArrayList<>())
          .add(enrollmentModel);
    }

    return irregularStudentsGroupedByEquivalentDegree;
  }
}
