package az.ibrahim.bookstoretask.controller;

import az.ibrahim.bookstoretask.dto.response.BookResponse;
import az.ibrahim.bookstoretask.dto.response.StudentResponse;
import az.ibrahim.bookstoretask.dto.response.SuccessResponse;
import az.ibrahim.bookstoretask.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Student Controller", description = "Handles student-related transactions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "Get all students", description = "Retrieves a list of all students.")
    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        List<StudentResponse> studentResponseList = studentService.findAll();
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Students received successfully", studentResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get currently reading books of the authenticated student", description = "Retrieves a list of books currently being read by the authenticated student.")
    @GetMapping("/my-currently-reading")
    public ResponseEntity<SuccessResponse> getMyCurrentlyReadingBooks(@RequestHeader("Authorization") String token) {
        List<BookResponse> currentlyReadingBooks = studentService.findMyCurrentlyReadingBooks(token);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Currently reading books received successfully", currentlyReadingBooks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get currently reading books of a student by student ID", description = "Retrieves a list of books currently being read by the student with the given ID.")
    @GetMapping("/{studentId}/currently-reading")
    public ResponseEntity<SuccessResponse> getMyCurrentlyReadingBooks(@PathVariable int studentId) {
        List<BookResponse> currentlyReadingBooks = studentService.findCurrentlyReadingBooksByStudentId(studentId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Currently reading books for Student ID", currentlyReadingBooks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Start reading a book by book ID", description = "Adds a book to the currently reading list of the authenticated student.")
    @PostMapping("/{bookId}/read-book")
    public ResponseEntity<SuccessResponse> startReadingBook(@PathVariable int bookId, @RequestHeader("Authorization") String token) {
        StudentResponse studentResponse = studentService.startReadingBook(bookId, token);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "The book has been successfully added", studentResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Subscribe to an author by author ID", description = "Subscribes the authenticated student to the author with the given ID.")
    @PostMapping("/subscribe/{authorId}")
    public ResponseEntity<String> subscribeToAuthor(@PathVariable int authorId, @RequestHeader("Authorization") String token) {
        studentService.subscribeToAuthor(authorId, token);
        return ResponseEntity.ok("You have successfully subscribed to the author with ID: " + authorId);
    }

    @Operation(summary = "Unsubscribe from an author by author ID", description = "Unsubscribes the authenticated student from the author with the given ID.")
    @PostMapping("/unsubscribe/{authorId}")
    public ResponseEntity<String> unsubscribeFromAuthor(@PathVariable int authorId, @RequestHeader("Authorization") String token) {
        studentService.unsubscribeFromAuthor(authorId, token);
        return ResponseEntity.ok(" You have successfully unsubscribed from the author with ID " + authorId);
    }

}
