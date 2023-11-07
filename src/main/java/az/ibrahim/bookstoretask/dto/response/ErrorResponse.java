package az.ibrahim.bookstoretask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String status;
    private int httpStatusCode;
    private String message;
    private String path;

    public ErrorResponse(int httpStatusCode, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = "error";
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.path = path;
    }
}
