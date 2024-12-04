package bg.tu_varna.sit.library.models.search;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SearchOutputModel implements OperationOutput {

    private List<Book> books;
}
