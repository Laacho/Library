package bg.tu_varna.sit.library.models.find_book_by_id;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class FindBookByIdInputModel implements OperationInput {
    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be a positive number")
    private Long id;
}
