package bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfBookExistsInAlreadyReadOutputModel implements OperationOutput {
    private Boolean foundIfExists;
}
