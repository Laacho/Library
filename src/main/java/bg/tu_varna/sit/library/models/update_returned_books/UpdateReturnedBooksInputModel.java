package bg.tu_varna.sit.library.models.update_returned_books;

import bg.tu_varna.sit.library.models.base.OperationInput;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReturnedBooksInputModel implements OperationInput {
    private Long userId;
    private BooksForReturn books;
}
