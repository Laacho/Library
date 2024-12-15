package bg.tu_varna.sit.library.models.update_returned_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReturnedBooksOutputModel implements OperationOutput {
    private String message;
}
