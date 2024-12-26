package bg.tu_varna.sit.library.models.send_verification_code_for_new_email;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendVerificationCodeForNewEmailInputModel implements OperationInput {
    private String newEmail;

}
