package mx.uady.sicei.kardex_service.repositories;

import mx.uady.sicei.kardex_service.schemas.StudentSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentSchema, String> {}
