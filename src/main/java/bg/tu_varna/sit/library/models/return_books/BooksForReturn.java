package bg.tu_varna.sit.library.models.return_books;

import bg.tu_varna.sit.library.data.entities.Book;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BooksForReturn {
    private Set<Book> books;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    private LocalDate deadline;
}
