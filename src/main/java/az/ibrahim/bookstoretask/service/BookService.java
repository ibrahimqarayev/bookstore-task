package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.request.CreateBookRequest;
import az.ibrahim.bookstoretask.dto.response.BookResponse;
import az.ibrahim.bookstoretask.dto.response.StudentSummaryResponse;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.Student;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final DtoConversionService conversionService;

    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return conversionService.toBookResponseList(books);
    }

    public List<StudentSummaryResponse> findReadersById(int bookId) {
        Book book = findById(bookId);
        List<Student> readers = book.getReaders();
        return conversionService.toStudentSummaryResponseList(readers);
    }

    public Book create(CreateBookRequest createBookRequest, Author author) {
        Book book = new Book();
        book.setName(createBookRequest.getName());
        book.setAuthor(author);
        return save(book);
    }

    public void deleteBookById(int bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book findById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

}
