package az.ibrahim.bookstoretask.repository;

import az.ibrahim.bookstoretask.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
