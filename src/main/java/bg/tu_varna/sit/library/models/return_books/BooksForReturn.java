package bg.tu_varna.sit.library.models.return_books;

import bg.tu_varna.sit.library.data.entities.Book;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BooksForReturn {
    @NotNull(message = "Books set must not be null")
    @Size(min = 1, message = "At least one book must be included")
    private Set<
            @NotNull(message = "Each book must not be null")
            Book> books;

    @NotNull(message = "Borrowing date must not be null")
    @PastOrPresent(message = "Borrowing date must be today or in the past")
    private LocalDate borrowingDate;

    @NotNull(message = "Return date must not be null")
    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDate returnDate;

    @NotNull(message = "Deadline must not be null")
    @FutureOrPresent(message = "Deadline must be today or in the future")
    private LocalDate deadline;
}
