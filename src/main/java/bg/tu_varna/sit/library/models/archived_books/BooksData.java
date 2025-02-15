package bg.tu_varna.sit.library.models.archived_books;


import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.entities.Location;
import bg.tu_varna.sit.library.data.entities.Publisher;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class BooksData {
    private String isbn;
    private String title;
    private String inventoryNumber;
    private BigDecimal price;
    private Genre genre;
    private Publisher publisher;
    private Set<Author> authors;
    private Location location;
    private LocalDate archivedDate;
    private String path;

}
