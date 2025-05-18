package mx.uady.sicei.kardex_service.repositories;

import mx.uady.sicei.kardex_service.schemas.TeacherSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherSchema, String> {}
