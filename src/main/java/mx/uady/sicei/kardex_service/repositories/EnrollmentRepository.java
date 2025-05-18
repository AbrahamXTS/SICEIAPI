package mx.uady.sicei.kardex_service.repositories;

import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentSchema, String> {}
