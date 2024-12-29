package bg.tu_varna.sit.library.models.search_for_user;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationOutput;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchForUserOutputModel implements OperationOutput{
    private List<Book> result;
}
