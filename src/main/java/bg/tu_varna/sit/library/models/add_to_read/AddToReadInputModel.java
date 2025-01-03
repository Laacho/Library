package bg.tu_varna.sit.library.models.add_to_read;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddToReadInputModel implements OperationInput {
    private CommonBooksProperties commonBooksProperties;
    private Boolean wantsToDelete;
}
