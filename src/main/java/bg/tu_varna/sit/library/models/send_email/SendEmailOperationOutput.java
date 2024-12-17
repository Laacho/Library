package bg.tu_varna.sit.library.models.send_email;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SendEmailOperationOutput implements OperationOutput {
    private String message;
}
