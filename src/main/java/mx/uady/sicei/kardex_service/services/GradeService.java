package mx.uady.sicei.kardex_service.services;

import mx.uady.sicei.kardex_service.dto.grade.CreateGradeDTO;
import mx.uady.sicei.kardex_service.dto.grade.UpdateGradeDTO;
import mx.uady.sicei.kardex_service.exceptions.ResourceNotFoundException;
import mx.uady.sicei.kardex_service.mappers.GradeMapper;
import mx.uady.sicei.kardex_service.models.Grade;
import mx.uady.sicei.kardex_service.repositories.GradeRepository;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import mx.uady.sicei.kardex_service.schemas.GradeSchema;
import org.springframework.stereotype.Service;

@Service
public class GradeService {
  private final GradeMapper gradeMapper;
  private final GradeRepository gradeRepository;
  private final EnrollmentService enrollmentService;

  public GradeService(
      GradeMapper gradeMapper,
      GradeRepository gradeRepository,
      EnrollmentService enrollmentService) {
    this.gradeMapper = gradeMapper;
    this.gradeRepository = gradeRepository;
    this.enrollmentService = enrollmentService;
  }

  public Grade createGrade(CreateGradeDTO gradeRequest) {
    EnrollmentSchema enrollment =
        enrollmentService.findEnrollmentByIdOrThrowAnException(gradeRequest.getEnrollmentId());

    GradeSchema grade =
        gradeRepository.save(
            GradeSchema.builder().enrollment(enrollment).score(gradeRequest.getScore()).build());

    return gradeMapper.toModel(grade);
  }

  public Grade updateGrade(UpdateGradeDTO gradeRequest) {
    GradeSchema grade = this.findGradeByIdOrThrowAnException(gradeRequest.getId());

    grade.setScore(gradeRequest.getScore());

    return gradeMapper.toModel(gradeRepository.save(grade));
  }

  public GradeSchema findGradeByIdOrThrowAnException(String gradeId) {
    return gradeRepository
        .findById(gradeId)
        .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));
  }
}
