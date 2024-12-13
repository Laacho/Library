package bg.tu_varna.sit.library.models.update_borrowed_books;

import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateBorrowedBooksInputModel implements OperationInput {
    private List<BooksForApproveData> books;
}
