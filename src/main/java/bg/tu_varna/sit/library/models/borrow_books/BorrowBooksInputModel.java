package bg.tu_varna.sit.library.models.borrow_books;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class BorrowBooksInputModel implements OperationInput {
    @NotNull(message = "Borrow date must not be null")
    private LocalDate borrowDate;

    @NotNull(message = "Return date must not be null")
    @Future(message = "Return date must be in the future")
    private LocalDate returnDate;

    @NotEmpty(message = "Books must not be empty")
    private Set<
            @Valid
            @NotNull(message = "CommonBooksProperties must not be null")
            CommonBooksProperties> books;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 1, max = 30, message = "Username must be between 1 and 30 characters")
    private String username;

}
