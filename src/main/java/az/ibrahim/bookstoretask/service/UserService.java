package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.dto.request.RegisterRequest;
import az.ibrahim.bookstoretask.entity.Student;
import az.ibrahim.bookstoretask.entity.User;
import az.ibrahim.bookstoretask.enums.Role;
import az.ibrahim.bookstoretask.exception.AccessDeniedException;
import az.ibrahim.bookstoretask.exception.DuplicateResourceException;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User findUserFromToken(String token) {

        String username = jwtService.extractUsername(token);

        if (username == null) {
            throw new BadCredentialsException("Received invalid token");
        }
        return findByUsername(username);
    }

    public void checkAuthorRole(User user) {
        if (user.getRole() != Role.AUTHOR) {
            throw new AccessDeniedException("Only authors can access this endpoint");
        }
    }

    public void checkStudentRole(User user) {
        if (user.getRole() != Role.STUDENT) {
            throw new AccessDeniedException("Only students can access this endpoint");
        }
    }


    public User createStudent(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.STUDENT);
        return save(user);
    }

    public User createAuthor(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.AUTHOR);
        return save(user);
    }


    @Transactional
    public User save(User user) {
        validateDuplicateUsername(user.getUsername());
        validateDuplicateEmail(user.getEmail());
        return userRepository.save(user);
    }

    public void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new DuplicateResourceException("Email already taken: " + email);
    }

    public void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateResourceException("Username already taken: " + username);
    }

    public User findById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

}
