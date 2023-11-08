package az.ibrahim.bookstoretask.controller;

import az.ibrahim.bookstoretask.dto.request.LoginRequest;
import az.ibrahim.bookstoretask.dto.request.RegisterRequest;
import az.ibrahim.bookstoretask.dto.response.AuthenticationResponse;
import az.ibrahim.bookstoretask.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller", description = "Handles user authentication and registration")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new author", description = "Registers a new author and returns an authentication token.")
    @PostMapping("/register-author")
    public ResponseEntity<AuthenticationResponse> registerAuthor(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse response = authenticationService.registerAuthor(registerRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new student", description = "Registers a new student and returns an authentication token.")
    @PostMapping("/register-student")
    public ResponseEntity<AuthenticationResponse> registerStudent(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse response = authenticationService.registerStudent(registerRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User login", description = "Logs in a user and returns an authentication token.")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}
