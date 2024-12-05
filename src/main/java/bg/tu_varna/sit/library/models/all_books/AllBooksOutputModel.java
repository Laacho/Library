package bg.tu_varna.sit.library.models.all_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AllBooksOutputModel implements OperationOutput {
   private List<BooksData> booksData;
}
