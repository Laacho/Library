package bg.tu_varna.sit.library.models.send_email_with_code;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendEmailWithCodeInputModel implements OperationInput {
    private String toEmail;
}
