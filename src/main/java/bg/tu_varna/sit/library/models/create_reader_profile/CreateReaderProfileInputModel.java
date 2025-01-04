package bg.tu_varna.sit.library.models.create_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CreateReaderProfileInputModel implements OperationInput {
    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be a positive number")
    private Long id;
}
