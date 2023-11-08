package az.ibrahim.bookstoretask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSummaryResponse {
    private int id;
    private String name;
}
