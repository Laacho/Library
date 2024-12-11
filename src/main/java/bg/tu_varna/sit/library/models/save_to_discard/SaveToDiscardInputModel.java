package bg.tu_varna.sit.library.models.save_to_discard;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SaveToDiscardInputModel implements OperationInput {
    private Book book;
    private String reason;
}
