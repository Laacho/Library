package bg.tu_varna.sit.library.models.reset_password_for_user;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ResetPasswordForUserOutputModel implements OperationOutput {
    private String message;
}
