package mx.uady.sicei.kardex_service.mappers;

import java.util.List;
import mx.uady.sicei.kardex_service.models.Enrollment;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {CourseMapper.class, GradeMapper.class, StudentMapper.class})
public interface EnrollmentMapper {
  Enrollment toModel(EnrollmentSchema student);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  EnrollmentSchema toSchema(Enrollment student);

  List<Enrollment> toModelList(List<EnrollmentSchema> students);

  List<EnrollmentSchema> toSchemaList(List<Enrollment> students);
}
