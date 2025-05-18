package mx.uady.sicei.kardex_service.mappers;

import java.util.List;
import mx.uady.sicei.kardex_service.models.Student;
import mx.uady.sicei.kardex_service.schemas.StudentSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
  Student toModel(StudentSchema student);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "enrollments", ignore = true)
  StudentSchema toSchema(Student student);

  List<Student> toModelList(List<StudentSchema> students);

  List<StudentSchema> toSchemaList(List<Student> students);
}
