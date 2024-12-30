package bg.tu_varna.sit.library.models.borrow_books;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class BorrowBooksInputModel implements OperationInput {
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Set<CommonBooksProperties> books;
    private String username;

}
