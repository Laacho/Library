package bg.tu_varna.sit.library.models.change_password;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ChangePasswordInputModel implements OperationInput {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
