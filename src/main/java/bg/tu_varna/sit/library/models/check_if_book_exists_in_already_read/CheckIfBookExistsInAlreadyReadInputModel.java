package bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfBookExistsInAlreadyReadInputModel implements OperationInput {
    @NotNull(message = "CommonBooksProperties must not be null")
    private CommonBooksProperties commonBooksProperties;
}
