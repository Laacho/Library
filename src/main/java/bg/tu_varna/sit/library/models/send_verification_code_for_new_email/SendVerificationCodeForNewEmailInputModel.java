package bg.tu_varna.sit.library.models.send_verification_code_for_new_email;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendVerificationCodeForNewEmailInputModel implements OperationInput {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String newEmail;

}
