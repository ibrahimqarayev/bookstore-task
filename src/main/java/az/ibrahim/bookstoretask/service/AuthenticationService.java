package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.request.LoginRequest;
import az.ibrahim.bookstoretask.dto.request.RegisterRequest;
import az.ibrahim.bookstoretask.dto.response.AuthenticationResponse;
import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Student;
import az.ibrahim.bookstoretask.entity.User;
import az.ibrahim.bookstoretask.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthorService authorService;
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse registerAuthor(RegisterRequest registerRequest) {
        User user = userService.createAuthor(registerRequest);

        Author author = new Author();
        author.setName(registerRequest.getName());
        author.setAge(registerRequest.getAge());
        author.setUser(user);
        authorService.save(author);

        var jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse registerStudent(RegisterRequest registerRequest) {
        User user = userService.createStudent(registerRequest);

        Student student = new Student();
        student.setName(registerRequest.getName());
        student.setAge(registerRequest.getAge());
        student.setUser(user);
        studentService.save(student);

        var jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        User user = userService.findByUsername(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("invalid username or password");
        }

        var jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }


}
