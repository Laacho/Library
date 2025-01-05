package bg.tu_varna.sit.library.models.add_genre;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CheckGenreInputModel implements OperationInput {
    @NotBlank(message = "Genre must not be blank")
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    private String genre;
}
