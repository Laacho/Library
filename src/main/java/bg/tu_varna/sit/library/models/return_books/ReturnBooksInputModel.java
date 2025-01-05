package bg.tu_varna.sit.library.models.return_books;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;


@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBooksInputModel implements OperationInput {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;
}
