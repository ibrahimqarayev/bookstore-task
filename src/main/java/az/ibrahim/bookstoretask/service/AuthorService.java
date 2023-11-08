package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.response.AuthorResponse;
import az.ibrahim.bookstoretask.dto.response.BookSummaryResponse;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.User;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final DtoConversionService conversionService;
    private final UserService userService;


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


    public Author findAuthorFromToken(String token) {
        User user = userService.findUserFromToken(token);
        userService.checkAuthorRole(user);
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
