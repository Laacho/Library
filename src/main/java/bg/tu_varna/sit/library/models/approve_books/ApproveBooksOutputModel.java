package bg.tu_varna.sit.library.models.approve_books;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApproveBooksOutputModel implements OperationOutput {
    private List<BooksForApproveData> books;
    private Map<String, List<Book>> booksWithSameISBN;
}
