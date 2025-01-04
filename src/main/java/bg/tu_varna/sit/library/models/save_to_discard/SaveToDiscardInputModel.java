package bg.tu_varna.sit.library.models.save_to_discard;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SaveToDiscardInputModel implements OperationInput {
    @NotNull(message = "Book must not be null")
    private Book book;
    @Size(min = 10, message = "Reason must be at least 10 characters long")
    @NotBlank(message = "Reason cannot be blank")
    private String reason;
}
