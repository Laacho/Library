package bg.tu_varna.sit.library.models.borrow_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class BorrowBooksOutputModel implements OperationOutput {
    private String message;
}
