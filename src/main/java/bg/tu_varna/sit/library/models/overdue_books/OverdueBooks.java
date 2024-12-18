package bg.tu_varna.sit.library.models.overdue_books;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OverdueBooks {
    private User user;
    private Set<Book> books;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    private LocalDate deadline;
}
