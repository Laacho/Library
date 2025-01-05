package bg.tu_varna.sit.library.models.update_borrowed_books;

import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.models.base.OperationInput;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.*;

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

//            @NotNull(message = "BooksForApproveData must not be null")
                    BooksForApproveData> books;
}
