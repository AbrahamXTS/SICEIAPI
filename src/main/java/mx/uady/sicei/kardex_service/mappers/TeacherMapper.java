package mx.uady.sicei.kardex_service.mappers;

import java.util.List;
import mx.uady.sicei.kardex_service.models.Teacher;
import mx.uady.sicei.kardex_service.schemas.TeacherSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
  Teacher toModel(TeacherSchema teacher);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "coursesTaught", ignore = true)
  TeacherSchema toSchema(Teacher teacher);

  List<Teacher> toModelList(List<TeacherSchema> teachers);

  List<TeacherSchema> toSchemaList(List<Teacher> teachers);
}
