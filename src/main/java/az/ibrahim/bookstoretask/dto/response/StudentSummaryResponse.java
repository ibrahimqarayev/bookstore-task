package az.ibrahim.bookstoretask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentSummaryResponse {
    private int id;
    private String name;
    private int age;
}
