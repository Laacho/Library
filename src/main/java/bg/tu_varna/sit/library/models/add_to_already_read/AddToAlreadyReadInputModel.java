package bg.tu_varna.sit.library.models.add_to_already_read;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import jakarta.validation.Valid;
import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddToAlreadyReadInputModel implements OperationInput {
    @Valid
    @NotNull(message = "CommonBooksProperties must not be null")
    private CommonBooksProperties commonBooksProperties;

    @NotNull(message = "WantToDelete must not be null")
    private Boolean wantToDelete;

}
