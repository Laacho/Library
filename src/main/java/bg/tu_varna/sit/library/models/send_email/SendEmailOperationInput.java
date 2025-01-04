package bg.tu_varna.sit.library.models.send_email;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SendEmailOperationInput implements OperationInput {

    @NotBlank(message = "Body must not be blank")
    @Size(min = 10, max = 500, message = "Body must be between 10 and 500 characters")
    private String body;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Recipient email must not be blank")
    @Email(message = "Recipient email must be a valid email address")
    private String toEmail;
}
