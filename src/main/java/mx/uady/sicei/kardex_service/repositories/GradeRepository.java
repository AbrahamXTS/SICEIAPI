package mx.uady.sicei.kardex_service.repositories;

import mx.uady.sicei.kardex_service.schemas.GradeSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeSchema, String> {}
