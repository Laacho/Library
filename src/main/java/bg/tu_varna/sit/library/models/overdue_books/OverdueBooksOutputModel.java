package bg.tu_varna.sit.library.models.overdue_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class OverdueBooksOutputModel implements OperationOutput {
    private List<OverdueBooks> overdueBooks;
}