package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.request.CreateBookRequest;
import az.ibrahim.bookstoretask.dto.response.AuthorResponse;
import az.ibrahim.bookstoretask.dto.response.BookResponse;
import az.ibrahim.bookstoretask.dto.response.StudentResponse;
import az.ibrahim.bookstoretask.dto.response.StudentSummaryResponse;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.Student;
import az.ibrahim.bookstoretask.exception.AccessDeniedException;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final DtoConversionService conversionService;
    private final StudentService studentService;
    private final AuthorService authorService;
    private final NotificationService notificationService;

    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return conversionService.toBookResponseList(books);
    }

    public List<StudentSummaryResponse> findReadersById(int bookId) {
        Book book = findById(bookId);
        List<Student> readers = book.getReaders();
        return conversionService.toStudentSummaryResponseList(readers);
    }

    public BookResponse startReadingBook(int bookId, String token) {
        Book book = findById(bookId);
        studentService.addBookToCurrentlyReadingList(book, token);
        return conversionService.toBookResponse(book);
    }


    public void deleteBookById(int bookId, String token) {

        Author author = authorService.findAuthorFromToken(token);

        Optional<Book> bookToDelete = author.getAuthoredBooks()
                .stream()
                .filter(book -> book.getId() == bookId)
                .findFirst();

        if (bookToDelete.isPresent()) {
            Book book = bookToDelete.get();

            if (book.getAuthor().getId().equals(author.getId())) {
                deleteBookById(bookId);
            } else {
                throw new ResourceNotFoundException("Book not found with ID: " + bookId);
            }
        } else {
            throw new AccessDeniedException("You are not authorized to delete this book");
        }
    }

    public BookResponse publishNewBook(CreateBookRequest createBookRequest, String token) {
        Author author = authorService.findAuthorFromToken(token);
        Book newBook = create(createBookRequest, author);
        notificationService.notifyStudentsAboutNewBook(author, newBook);
        return conversionService.toBookResponse(newBook);
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
