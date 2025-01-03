package bg.tu_varna.sit.library.models.return_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBooksOutputModel implements OperationOutput {
   private List<BooksForReturn> booksForReturns;
   private Long userId;
}
