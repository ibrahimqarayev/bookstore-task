package az.ibrahim.bookstoretask.controller;

import az.ibrahim.bookstoretask.dto.request.CreateBookRequest;
import az.ibrahim.bookstoretask.dto.response.*;
import az.ibrahim.bookstoretask.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book Controller", description = "Handles book-related transactions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieves a list of all books.")
    @GetMapping
    public ResponseEntity<SuccessResponse> getAllBooks() {
        List<BookResponse> bookResponseList = bookService.findAll();
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "All books received successfully", bookResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Start reading a book by book ID", description = "Adds a book to the currently reading list of the authenticated student.")
    @PostMapping("/{bookId}/read-book")
    public ResponseEntity<SuccessResponse> startReadingBook(@PathVariable int bookId, @RequestHeader("Authorization") String token) {
        BookResponse bookResponse = bookService.startReadingBook(bookId, token);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "The book '" + bookResponse.getName() + "' has been successfully added to your currently reading list.", bookResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get readers of a book by book ID", description = "Retrieves a list of students who are currently reading the book.")
    @GetMapping("/{bookId}/readers")
    public ResponseEntity<SuccessResponse> getReadersOfBook(@PathVariable int bookId) {
        List<StudentSummaryResponse> studentSummaryResponseList = bookService.findReadersById(bookId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Readers of Book ID " + bookId + " received successfully", studentSummaryResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a book by book ID", description = "Deletes a book with the specified book ID.")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBookById(@PathVariable int bookId, @RequestHeader("Authorization") String token) {
        bookService.deleteBookById(bookId, token);
        return ResponseEntity.ok("Book with ID " + bookId + " has been deleted.");
    }

    @Operation(summary = "Publish a new book", description = "Publishes a new book for the author and returns the book information. When a book is published, an email is sent to the subscribed students.")
    @PostMapping("/publish-book")
    public ResponseEntity<SuccessResponse> publishNewBook(@Valid @RequestBody CreateBookRequest createBookRequest, @RequestHeader("Authorization") String token) {
        BookResponse bookResponse = bookService.publishNewBook(createBookRequest, token);
        SuccessResponse response = new SuccessResponse(HttpStatus.CREATED.value(), "Book created successfully and an email notification has been sent to subscribed students.", bookResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
