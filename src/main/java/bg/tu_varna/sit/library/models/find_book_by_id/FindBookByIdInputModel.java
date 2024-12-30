package bg.tu_varna.sit.library.models.find_book_by_id;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class FindBookByIdInputModel implements OperationInput {
    private Long id;
}
