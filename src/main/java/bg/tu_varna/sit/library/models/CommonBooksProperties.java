package bg.tu_varna.sit.library.models;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CommonBooksProperties {
    @Size(min = 5, max = 20, message = "ISBN must be between 5 and 20 characters long")
    @NotBlank(message = "ISBN must not be blank")
    private String isbn;

    @Size(min = 1,message = "Size must be min 1")
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Inventory number must not be blank")
    @Size(max = 50, message = "Inventory number must not exceed 50 characters")
    private String inventoryNumber;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    @NotBlank(message = "Genre must not be blank")
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    private String genre;

    @NotBlank(message = "Publisher must not be blank")
    @Size(max = 255, message = "Publisher must not exceed 255 characters")
    private String publisher;

    @NotEmpty(message = "Authors list must not be empty")
    private Set<@NotBlank(message = "Author name must not be blank")
                String> authors;

    @NotBlank(message = "Path must not be blank")
    @Size(max = 255, message = "Path must not exceed 255 characters")
    private String pathImage;
}
