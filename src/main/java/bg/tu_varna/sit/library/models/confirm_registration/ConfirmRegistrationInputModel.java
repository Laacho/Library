package bg.tu_varna.sit.library.models.confirm_registration;

import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ConfirmRegistrationInputModel implements OperationInput {
    private String verificationCode;
}
