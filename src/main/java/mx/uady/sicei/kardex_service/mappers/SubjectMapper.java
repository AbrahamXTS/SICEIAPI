package mx.uady.sicei.kardex_service.mappers;

import java.util.List;
import mx.uady.sicei.kardex_service.models.Subject;
import mx.uady.sicei.kardex_service.schemas.SubjectSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
  Subject toModel(SubjectSchema subject);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "courses", ignore = true)
  SubjectSchema toSchema(Subject subject);

  List<Subject> toModelList(List<SubjectSchema> subjects);

  List<SubjectSchema> toSchemaList(List<Subject> subjects);
}
