package bg.tu_varna.sit.library.models.approve_books;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.User;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BooksForApproveData {
    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be a positive number")
    private Long id;
    @NotNull(message = "User must not be null")
    private User user;
    @NotNull(message = "Borrowing date must not be null")
    @PastOrPresent(message = "Borrowing date must be today or in the past")
    private LocalDate borrowingDate;
    @NotNull(message = "Return date must not be null")
    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDate returnDate;
    @NotNull(message = "Books set must not be null")
    @Size(min = 1, message = "At least one book must be included")
    private Set<Book> books;
    @NotNull(message = "Deadline must not be null")
    @FutureOrPresent(message = "Deadline must be today or in the future")
    private LocalDate deadline;
}
