package mx.uady.sicei.kardex_service.mappers;

import java.util.List;
import mx.uady.sicei.kardex_service.models.Grade;
import mx.uady.sicei.kardex_service.schemas.GradeSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {
  Grade toModel(GradeSchema student);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "enrollment", ignore = true)
  GradeSchema toSchema(Grade student);

  List<Grade> toModelList(List<GradeSchema> students);

  List<GradeSchema> toSchemaList(List<Grade> students);
}
