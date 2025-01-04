package bg.tu_varna.sit.library.models.reset_password_for_user;

import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ResetPasswordForUserInputModel implements OperationInput {
    @NotNull(message = "User credentials must not be null")
    private UserCredentials userCredentials;
}
