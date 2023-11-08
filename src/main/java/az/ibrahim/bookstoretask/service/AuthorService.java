package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.request.CreateBookRequest;
import az.ibrahim.bookstoretask.dto.response.AuthorResponse;
import az.ibrahim.bookstoretask.dto.response.BookResponse;
import az.ibrahim.bookstoretask.dto.response.BookSummaryResponse;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.User;
import az.ibrahim.bookstoretask.exception.AccessDeniedException;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final DtoConversionService conversionService;
    private final UserService userService;
    private final BookService bookService;
    private final NotificationService notificationService;

    public List<AuthorResponse> findAll() {
        List<Author> authors = authorRepository.findAll();
        return conversionService.toAuthorResponseList(authors);
    }

    public List<BookSummaryResponse> findAuthoredBooksById(int authorId) {
        Author author = findById(authorId);
        List<Book> authoredBooks = author.getAuthoredBooks();

        if (authoredBooks.isEmpty()) {
            throw new ResourceNotFoundException("No books found for Author ID: " + authorId);
        }
        return conversionService.toBookSummaryResponseList(authoredBooks);
    }

    public void deleteBookById(int bookId, String token) {

        Author author = findAuthorFromToken(token);

        Optional<Book> bookToDelete = author.getAuthoredBooks()
                .stream()
                .filter(book -> book.getId() == bookId)
                .findFirst();

        if (bookToDelete.isPresent()) {
            Book book = bookToDelete.get();

            if (book.getAuthor().getId().equals(author.getId())) {
                bookService.deleteBookById(bookId);
            } else {
                throw new ResourceNotFoundException("Book not found with ID: " + bookId);
            }
        } else {
            throw new AccessDeniedException("You are not authorized to delete this book");
        }
    }


    public AuthorResponse publishNewBook(CreateBookRequest createBookRequest, String token) {
        Author author = findAuthorFromToken(token);
        Book newBook = bookService.create(createBookRequest, author);
        notificationService.notifyStudentsAboutNewBook(author, newBook);
        return conversionService.toAuthorResponse(author);
    }

    public Author findAuthorFromToken(String token) {
        User user = userService.findUserFromToken(token);
        return findByUserId(user.getId());
    }

    protected Author findById(int authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with authorId: " + authorId));
    }

    public Author findByUserId(int userId) {
        return authorRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with userId: " + userId));
    }

    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

}
