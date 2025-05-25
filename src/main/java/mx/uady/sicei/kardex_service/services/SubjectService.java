package mx.uady.sicei.kardex_service.services;

import java.util.Collections;
import java.util.List;
import mx.uady.sicei.kardex_service.dto.subject.CreateSubjectDTO;
import mx.uady.sicei.kardex_service.dto.subject.UpdateSubjectDTO;
import mx.uady.sicei.kardex_service.exceptions.ResourceNotFoundException;
import mx.uady.sicei.kardex_service.mappers.SubjectMapper;
import mx.uady.sicei.kardex_service.models.Subject;
import mx.uady.sicei.kardex_service.repositories.SubjectRepository;
import mx.uady.sicei.kardex_service.schemas.SubjectSchema;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
  private final SubjectMapper subjectMapper;
  private final SubjectRepository subjectRepository;

  public SubjectService(SubjectMapper subjectMapper, SubjectRepository subjectRepository) {
    this.subjectMapper = subjectMapper;
    this.subjectRepository = subjectRepository;
  }

  public List<Subject> getAllSubjects() {
    List<SubjectSchema> subjects = subjectRepository.findAll();

    return subjectMapper.toModelList(subjects);
  }

  public Subject createSubject(CreateSubjectDTO subjectRequest) {
    SubjectSchema subject =
        subjectRepository.save(
            SubjectSchema.builder()
                .name(subjectRequest.getName())
                .courses(Collections.emptyList())
                .build());

    return subjectMapper.toModel(subject);
  }

  public Subject updateSubject(UpdateSubjectDTO subjectRequest) {
    SubjectSchema subject = this.findSubjectByIdOrThrowAnException(subjectRequest.getId());

    subject.setName(subjectRequest.getName());

    return subjectMapper.toModel(subjectRepository.save(subject));
  }

  public void deleteSubject(String subjectId) {
    SubjectSchema subject = this.findSubjectByIdOrThrowAnException(subjectId);

    subjectRepository.delete(subject);
  }

  public SubjectSchema findSubjectByIdOrThrowAnException(String subjectId) {
    return subjectRepository
        .findById(subjectId)
        .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada."));
  }
}
