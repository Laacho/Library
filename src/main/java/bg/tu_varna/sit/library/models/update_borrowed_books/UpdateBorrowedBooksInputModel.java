package bg.tu_varna.sit.library.models.update_borrowed_books;

import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateBorrowedBooksInputModel implements OperationInput {
    @NotNull(message = "Approved books must not be null")
    @Size(min = 1, message = "At least one book must be included")
    private List<
            @Valid
            @NotNull(message = "BooksForApproveData must not be null")
            BooksForApproveData> books;
}
