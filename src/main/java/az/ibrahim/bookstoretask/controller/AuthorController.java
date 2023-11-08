package az.ibrahim.bookstoretask.controller;

import az.ibrahim.bookstoretask.dto.request.CreateBookRequest;
import az.ibrahim.bookstoretask.dto.response.AuthorResponse;
import az.ibrahim.bookstoretask.dto.response.BookSummaryResponse;
import az.ibrahim.bookstoretask.dto.response.SuccessResponse;
import az.ibrahim.bookstoretask.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Author Controller", description = "Handles author-related transactions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Operation(summary = "Get all authors", description = "Retrieves a list of all authors.")
    @GetMapping
    public ResponseEntity<SuccessResponse> getAllAuthors() {
        List<AuthorResponse> authorResponseList = authorService.findAll();
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "All authors received successfully", authorResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Publish a new book", description = "Publishes a new book for the author and returns the book information. When a book is published, an email is sent to the subscribed students.")
    @PostMapping("/publish-book")
    public ResponseEntity<SuccessResponse> publishNewBook(@Valid @RequestBody CreateBookRequest createBookRequest, @RequestHeader("Authorization") String token) {
        AuthorResponse authorResponse = authorService.publishNewBook(createBookRequest, token);
        SuccessResponse response = new SuccessResponse(HttpStatus.CREATED.value(), "Book created successfully", authorResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get authored books by author ID", description = "Retrieves a list of books authored by the specified author.")
    @GetMapping("/{authorId}/books")
    public ResponseEntity<SuccessResponse> getAuthoredBookById(@PathVariable int authorId) {
        List<BookSummaryResponse> authoredBooks = authorService.findAuthoredBooksById(authorId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Books by Author ID " + authorId + " received successfully", authoredBooks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a book by book ID", description = "Deletes a book with the specified book ID.")
    @DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<String> deleteBookById(@PathVariable int bookId, @RequestHeader("Authorization") String token) {
        authorService.deleteBookById(bookId, token);
        return ResponseEntity.ok("Book with ID " + bookId + " has been deleted.");
    }

}
