package az.ibrahim.bookstoretask.repository;

import az.ibrahim.bookstoretask.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<List<Book>> findByAuthorId(int authorId);
}
