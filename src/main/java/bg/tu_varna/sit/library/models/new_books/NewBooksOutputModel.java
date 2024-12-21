package bg.tu_varna.sit.library.models.new_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NewBooksOutputModel implements OperationOutput {
    private List<NewBooksData> newBooksData;
}
