package bg.tu_varna.sit.library.models;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.entities.Publisher;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CommonBooksProperties {
    private String isbn;
    private String title;
    private String inventoryNumber;
    private double price;
    private String genre;
    private String publisher;
    private Set<String> authors;
    private String pathImage;
}
