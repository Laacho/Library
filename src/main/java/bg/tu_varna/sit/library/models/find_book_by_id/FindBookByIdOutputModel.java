package bg.tu_varna.sit.library.models.find_book_by_id;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class FindBookByIdOutputModel implements OperationOutput {
    private CommonBooksProperties book;
}
