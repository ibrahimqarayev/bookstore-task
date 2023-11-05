package az.ibrahim.bookstoretask.repository;

import az.ibrahim.bookstoretask.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("SELECT a from Author a WHERE a.user.id =:userId")
    Optional<Author> findByUserId(@Param("userId") Integer userId);
}
