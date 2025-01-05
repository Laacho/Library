package bg.tu_varna.sit.library.models.update_returned_books;

import bg.tu_varna.sit.library.models.base.OperationInput;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReturnedBooksInputModel implements OperationInput {
    @NotNull(message = "User ID must not be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @Valid
    @NotNull(message = "Books for return must not be null")
    private BooksForReturn books;
}
