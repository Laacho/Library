package bg.tu_varna.sit.library.models.save_to_archived;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SaveInArchivedInputModel implements OperationInput {
    @NotNull(message = "Book must not be null")
    private Book book;
}
