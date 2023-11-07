package az.ibrahim.bookstoretask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentResponse {
    private Integer id;
    private String name;
    private int age;
    private List<BookResponse> currentlyReading;
    private List<String> subscribedAuthors;
}
