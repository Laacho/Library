package bg.tu_varna.sit.library.models.add_to_read;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddToReadInputModel implements OperationInput {
    @Valid
    @NotNull(message = "CommonBooksProperties must not be null")
    private CommonBooksProperties commonBooksProperties;

    @NotNull(message = "WantToDelete must not be null")
    private Boolean wantsToDelete;
}
