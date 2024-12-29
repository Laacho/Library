package bg.tu_varna.sit.library.models.send_email_with_code;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendEmailWithCodeOutputModel implements OperationOutput {
    private String message;
}
