package bg.tu_varna.sit.library.models.approve_books;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BooksForApproveData {
    private Long id;
    private User user;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    private Set<Book> books;
    private LocalDate deadline;
}
