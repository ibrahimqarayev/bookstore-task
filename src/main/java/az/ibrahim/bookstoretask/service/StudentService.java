package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.response.BookResponse;
import az.ibrahim.bookstoretask.dto.response.StudentResponse;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.Student;
import az.ibrahim.bookstoretask.entity.User;
import az.ibrahim.bookstoretask.exception.DuplicateResourceException;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final DtoConversionService conversionService;
    private final UserService userService;
    private final AuthorService authorService;


    public List<StudentResponse> findAll() {
        List<Student> students = studentRepository.findAll();
        return conversionService.toStudentResponseList(students);
    }

    public void subscribeToAuthor(int authorId, String token) {
        Author author = authorService.findById(authorId);
        Student student = findStudentFromToken(token);

        if (student.getSubscriptions().contains(author)) {
            throw new DuplicateResourceException("You are already subscribed to this author !");
        }

        student.getSubscriptions().add(author);
        studentRepository.save(student);
    }

    public void unsubscribeFromAuthor(int authorId, String token) {
        Author author = authorService.findById(authorId);

        Student student = findStudentFromToken(token);

        if (student.getSubscriptions().contains(author)) {
            student.getSubscriptions().remove(author);
            studentRepository.save(student);
        } else {
            throw new ResourceNotFoundException("You are not subscribed to this author !");
        }
    }


    public void addBookToCurrentlyReadingList(Book book, String token) {
        Student student = findStudentFromToken(token);
        student.getCurrentlyReading().add(book);
        save(student);
    }


    public List<BookResponse> findMyCurrentlyReadingBooks(String token) {
        Student student = findStudentFromToken(token);
        List<Book> currentlyReading = student.getCurrentlyReading();
        return conversionService.toBookResponseList(currentlyReading);
    }

    public List<BookResponse> findCurrentlyReadingBooksByStudentId(int studentId) {
        Student student = findById(studentId);
        List<Book> books = student.getCurrentlyReading();
        return conversionService.toBookResponseList(books);
    }

    protected Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudentFromToken(String token) {
        User user = userService.findUserFromToken(token);
        return findByUserId(user.getId());
    }

    public Student findByUserId(int userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with userId: " + userId));
    }

    private Student findById(int studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with studentId: " + studentId));
    }
}
