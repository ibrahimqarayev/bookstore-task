package az.ibrahim.bookstoretask.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "Book name is required")
    @Size(min = 3, max = 30, message = "Book name must be between 3 and 30 characters")
    private String name;
}
