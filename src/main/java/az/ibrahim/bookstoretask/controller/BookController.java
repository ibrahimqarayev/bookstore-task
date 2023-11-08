package az.ibrahim.bookstoretask.controller;

import az.ibrahim.bookstoretask.dto.response.BookResponse;
import az.ibrahim.bookstoretask.dto.response.StudentSummaryResponse;
import az.ibrahim.bookstoretask.dto.response.SuccessResponse;
import az.ibrahim.bookstoretask.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Get readers of a book by book ID", description = "Retrieves a list of students who are currently reading the book.")
    @GetMapping("/{bookId}/readers")
    public ResponseEntity<SuccessResponse> getReadersOfBook(@PathVariable int bookId) {
        List<StudentSummaryResponse> studentSummaryResponseList = bookService.findReadersById(bookId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Readers of Book ID " + bookId + " received successfully", studentSummaryResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
