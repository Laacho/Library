package bg.tu_varna.sit.library.models.return_books;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBooksInputModel implements OperationInput {
    private Long userId;
}
