package bg.tu_varna.sit.library.models.update_borrowed_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateBorrowedBooksOutputModel implements OperationOutput {
    private String meesage;
}
