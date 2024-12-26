package bg.tu_varna.sit.library.models.send_verification_code_for_new_email;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendVerificationCodeForNewEmailOutputModel implements OperationOutput {
    private String message;
}
