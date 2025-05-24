package mx.uady.sicei.kardex_service.services;

import java.util.Collections;
import java.util.List;
import mx.uady.sicei.kardex_service.dto.teacher.CreateTeacherDTO;
import mx.uady.sicei.kardex_service.dto.teacher.UpdateTeacherDTO;
import mx.uady.sicei.kardex_service.exceptions.ResourceNotFoundException;
import mx.uady.sicei.kardex_service.mappers.TeacherMapper;
import mx.uady.sicei.kardex_service.models.Teacher;
import mx.uady.sicei.kardex_service.repositories.TeacherRepository;
import mx.uady.sicei.kardex_service.schemas.TeacherSchema;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
  private final TeacherMapper teacherMapper;
  private final TeacherRepository teacherRepository;

  public TeacherService(TeacherMapper teacherMapper, TeacherRepository teacherRepository) {
    this.teacherMapper = teacherMapper;
    this.teacherRepository = teacherRepository;
  }

  public List<Teacher> getAllTeachers() {
    List<TeacherSchema> teachers = teacherRepository.findAll();

    return teacherMapper.toModelList(teachers);
  }

  public Teacher createTeacher(CreateTeacherDTO teacherRequest) {
    TeacherSchema teacher =
        teacherRepository.save(
            TeacherSchema.builder()
                .name(teacherRequest.getName())
                .lastName(teacherRequest.getLastName())
                .secondLastName(teacherRequest.getSecondLastName())
                .email(teacherRequest.getEmail())
                .coursesTaught(Collections.emptyList())
                .build());

    return teacherMapper.toModel(teacher);
  }

  public Teacher updateTeacher(UpdateTeacherDTO teacherRequest) {
    TeacherSchema teacher =
        this.findTeacherByEmplooyeeIdOrThrowAnException(teacherRequest.getEmployeeId());

    teacher.setName(teacherRequest.getName());
    teacher.setLastName(teacherRequest.getLastName());
    teacher.setSecondLastName(teacherRequest.getSecondLastName());
    teacher.setEmail(teacherRequest.getEmail());

    return teacherMapper.toModel(teacherRepository.save(teacher));
  }

  public void deleteTeacher(String teacherId) {
    TeacherSchema teacher = this.findTeacherByEmplooyeeIdOrThrowAnException(teacherId);

    teacherRepository.delete(teacher);
  }

  public TeacherSchema findTeacherByEmplooyeeIdOrThrowAnException(String teacherId) {
    return teacherRepository
        .findById(teacherId)
        .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
  }
}
