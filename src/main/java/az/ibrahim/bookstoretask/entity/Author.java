package az.ibrahim.bookstoretask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;
    private String age;

    @OneToMany(mappedBy = "author")
    private List<Book> authoredBooks = new ArrayList<>();

    @ManyToMany(mappedBy = "subscriptions")
    private Set<Student> subscribedStudents = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
