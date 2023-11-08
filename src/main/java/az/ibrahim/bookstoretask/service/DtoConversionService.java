package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.response.*;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DtoConversionService {

    // Book -> BookResponse
    public BookResponse toBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setName(book.getName());
        response.setAuthorName(book.getAuthor().getName());
        return response;
    }

    // List<Book> -> List<BookResponse>
    public List<BookResponse> toBookResponseList(List<Book> books) {
        return books.stream().map(this::toBookResponse).collect(Collectors.toList());
    }

    // Book -> BookSummaryResponse
    public BookSummaryResponse toBookSummaryResponse(Book book) {
        BookSummaryResponse bookSummaryResponse = new BookSummaryResponse();
        bookSummaryResponse.setId(book.getId());
        bookSummaryResponse.setName(book.getName());
        return bookSummaryResponse;
    }

    // List<Book> -> List<BookSummaryResponse>
    public List<BookSummaryResponse> toBookSummaryResponseList(List<Book> books) {
        return books.stream().map(this::toBookSummaryResponse).collect(Collectors.toList());
    }

    // Author -> AuthorResponse
    public AuthorResponse toAuthorResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        response.setAge(author.getAge());
        response.setAuthoredBooks(toBookSummaryResponseList(author.getAuthoredBooks()));
        return response;
    }

    // List<Author> -> List<AuthorResponse>
    public List<AuthorResponse> toAuthorResponseList(List<Author> authors) {
        return authors.stream().map(this::toAuthorResponse).collect(Collectors.toList());
    }

    // Student -> StudentResponse
    public StudentResponse toStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setAge(student.getAge());
        response.setCurrentlyReading(toBookResponseList(student.getCurrentlyReading()));
        response.setSubscribedAuthors(student.getSubscriptions().stream().map(Author::getName).collect(Collectors.toList()));
        return response;
    }

    // List<Student> -> List<StudentResponse>
    public List<StudentResponse> toStudentResponseList(List<Student> students) {
        return students.stream().map(this::toStudentResponse).collect(Collectors.toList());
    }

    // Student -> StudentSummaryResponse
    public StudentSummaryResponse toStudentSummaryResponse(Student student) {
        StudentSummaryResponse response = new StudentSummaryResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setAge(student.getAge());
        return response;
    }

    // List<Student> -> List<StudentSummaryResponse>
    public List<StudentSummaryResponse> toStudentSummaryResponseList(List<Student> students) {
        return students.stream().map(this::toStudentSummaryResponse).collect(Collectors.toList());
    }
}
