package bg.tu_varna.sit.library.models.change_username;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ChangeUsernameInputModel implements OperationInput {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 1, max = 30, message = "Username must be between 1 and 30 characters")
    private String username;
}
