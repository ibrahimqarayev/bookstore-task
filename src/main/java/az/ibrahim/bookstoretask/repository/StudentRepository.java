package az.ibrahim.bookstoretask.repository;

import az.ibrahim.bookstoretask.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s from Student s WHERE s.user.id =:userId")
    Optional<Student> findByUserId(@Param("userId") int userId);

    @Query("SELECT s FROM Student s JOIN s.subscriptions a WHERE a.id = :authorId")
    Optional<List<Student>> getSubscribedStudents(@Param("authorId") int authorId);
}
