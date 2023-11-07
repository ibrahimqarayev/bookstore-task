package az.ibrahim.bookstoretask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
    private LocalDateTime timestamp;
    private String status;
    private int httpStatusCode;
    private String message;
    private T data;


    public SuccessResponse(int httpStatusCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = "success";
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public SuccessResponse(int httpStatusCode, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = "success";
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = data;
    }
}
