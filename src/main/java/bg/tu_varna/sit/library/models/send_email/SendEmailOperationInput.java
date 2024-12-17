package bg.tu_varna.sit.library.models.send_email;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SendEmailOperationInput implements OperationInput {

    private String body;
    private String title;
    private String toEmail;
}
