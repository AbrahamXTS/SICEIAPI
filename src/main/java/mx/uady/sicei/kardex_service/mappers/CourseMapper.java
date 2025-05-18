package mx.uady.sicei.kardex_service.mappers;

import java.util.List;
import mx.uady.sicei.kardex_service.models.Course;
import mx.uady.sicei.kardex_service.schemas.CourseSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {SubjectMapper.class, TeacherMapper.class})
public interface CourseMapper {
  Course toModel(CourseSchema course);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "enrollments", ignore = true)
  @Mapping(target = "grades", ignore = true)
  CourseSchema toSchema(Course course);

  List<Course> toModelList(List<CourseSchema> courses);

  List<CourseSchema> toSchemaList(List<Course> courses);
}
