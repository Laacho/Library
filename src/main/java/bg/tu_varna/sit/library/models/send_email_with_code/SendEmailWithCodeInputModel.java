package bg.tu_varna.sit.library.models.send_email_with_code;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendEmailWithCodeInputModel implements OperationInput {
    @NotBlank(message = "Recipient email must not be blank")
    @Email(message = "Recipient email must be a valid email address")
    private String toEmail;
}
