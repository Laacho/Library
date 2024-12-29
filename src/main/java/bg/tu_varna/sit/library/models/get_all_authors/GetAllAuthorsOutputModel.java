package bg.tu_varna.sit.library.models.get_all_authors;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetAllAuthorsOutputModel implements OperationOutput {
    private List<Author> authors;
}
