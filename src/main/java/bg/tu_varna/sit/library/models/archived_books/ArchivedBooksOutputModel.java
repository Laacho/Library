package bg.tu_varna.sit.library.models.archived_books;

import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ArchivedBooksOutputModel implements OperationOutput {
   private List<BooksData> booksData;
}
