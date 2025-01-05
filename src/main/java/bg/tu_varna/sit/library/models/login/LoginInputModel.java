package bg.tu_varna.sit.library.models.login;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class LoginInputModel implements OperationInput {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 1, max = 30, message = "Username must be between 1 and 30 characters")
    private String username;

    @NotBlank(message = "New password must not be blank")
    @Size(min = 1, max = 30, message = "New password must be between 1 and 30 characters")
    private String password;
}
