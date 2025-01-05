package bg.tu_varna.sit.library.models.confirm_registration;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

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
