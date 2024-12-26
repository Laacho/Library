package bg.tu_varna.sit.library.models.change_password;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ChangePasswordOutputModel implements OperationOutput {
    private String message;
}
