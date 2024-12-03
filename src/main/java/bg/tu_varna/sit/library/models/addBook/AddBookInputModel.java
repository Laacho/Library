package bg.tu_varna.sit.library.models.addBook;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class AddBookInputModel implements OperationInput {
    private Set<Author> authors;
    private String isbn;
    private String title;
    private String genre;
    private String publisher;
    private String inventoryNumber;
    private BigDecimal price;
    private Long quantity;
    private Long row;

}
