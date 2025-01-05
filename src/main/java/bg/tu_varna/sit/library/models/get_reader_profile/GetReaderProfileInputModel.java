package bg.tu_varna.sit.library.models.get_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;
import jakarta.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetReaderProfileInputModel implements OperationInput {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 1, max = 30, message = "Username must be between 1 and 30 characters")
    private String username;
}
