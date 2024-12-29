package bg.tu_varna.sit.library.models.all_books;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.entities.Location;
import bg.tu_varna.sit.library.data.entities.Publisher;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BooksData {
    private String isbn;
    private String title;
    private String inventoryNumber;
    private BigDecimal price;
    private Genre genre;
    private Publisher publisher;
    private Location location;
    private Set<Author> authors;
    private String path;
}
