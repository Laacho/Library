package bg.tu_varna.sit.library.models.confirm_registration;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ConfirmRegistrationInputModel implements OperationInput {
    @NotBlank(message = "Verification code must not be blank")
    private String verificationCode;
}
