package az.ibrahim.bookstoretask.service;

import az.ibrahim.bookstoretask.entity.Author;
import az.ibrahim.bookstoretask.entity.Book;
import az.ibrahim.bookstoretask.entity.Student;
import az.ibrahim.bookstoretask.exception.ResourceNotFoundException;
import az.ibrahim.bookstoretask.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final StudentRepository studentRepository;
    private final JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);


    public void notifyStudentsAboutNewBook(Author author, Book newBook) {

        List<Student> subscribedStudents = studentRepository.getSubscribedStudents(author.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No subscribed students found for author with ID: " + author.getId()));

        try {

            for (Student student : subscribedStudents) {
                String to = student.getUser().getEmail();
                String subject = "New Book Notification: ";
                String message = "Hello " + student.getName() + ",\n\n"
                        + "A new book titled \"" + newBook.getName() + "\" has been published by author " + author.getName() + ".\n"
                        + "Happy reading!";

                sendEmail(to, subject, message);
            }
        } catch (Exception e) {
            logger.error("An error occurred while notifying students about a new book.", e);
        }

    }

    public void sendEmail(String to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("BookStore");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
            logger.info("Mail sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending email to: {}", to, e);
        }
    }


}
