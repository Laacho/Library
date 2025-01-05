package bg.tu_varna.sit.library.models.add_book;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class AddBookInputModel implements OperationInput {
    @NotEmpty(message = "Authors must not be empty")
    private Set<
            @NotNull(message = "Each author must not be null")
            Author> authors;

    @Size(min = 5, max = 20, message = "ISBN must be between 5 and 20 characters long")
    @NotBlank(message = "ISBN must not be blank")
    private String isbn;

    @Size(min = 1,message = "Size must be min 1")
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Genre must not be blank")
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    private String genre;

    @NotBlank(message = "Publisher must not be blank")
    @Size(max = 255, message = "Publisher must not exceed 255 characters")
    private String publisher;

    @NotBlank(message = "Inventory number must not be blank")
    @Size(max = 50, message = "Inventory number must not exceed 50 characters")
    private String inventoryNumber;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Row must not be null")
    @Positive(message = "Row must be a positive number")
    private Long row;

    @NotBlank(message = "Path must not be blank")
    @Size(max = 255, message = "Path must not exceed 255 characters")
    private String path;

}
