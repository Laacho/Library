package bg.tu_varna.sit.library.models.demote_user;

import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class DemoteUserInputModel implements OperationInput {
    @NotNull(message = "User credentials must not be null")
    private UserCredentials userCredentials;
}
