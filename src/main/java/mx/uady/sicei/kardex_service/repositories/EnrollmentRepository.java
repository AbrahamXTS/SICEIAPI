package mx.uady.sicei.kardex_service.repositories;

import java.util.List;
import mx.uady.sicei.kardex_service.schemas.EnrollmentSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentSchema, String> {
  List<EnrollmentSchema> findAllByCourseId(String courseId);

  List<EnrollmentSchema> findAllByStudentId(String studentId);

  @Query(
      """
          SELECT e
          FROM enrollment e
          WHERE e.grade IS NOT NULL
            AND e.student.id NOT IN (
                SELECT fail.student.id
                FROM enrollment fail
                WHERE fail.grade IS NOT NULL
                  AND fail.grade.score < 70
            )
      """)
  List<EnrollmentSchema> findEnrollmentsForStudentsWithOnlyPassingGrades();

  @Query(
      """
          SELECT e
          FROM enrollment e
          WHERE e.grade IS NOT NULL
            AND e.student.id IN (
                SELECT fail.student.id
                FROM enrollment fail
                WHERE fail.grade IS NOT NULL
                  AND fail.grade.score < 70
            )
      """)
  List<EnrollmentSchema> findEnrollmentsForStudentsWithFailingGrades();
}
