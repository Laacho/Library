package bg.tu_varna.sit.library.models.changeUsername;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ChangeUsernameInputModel implements OperationInput {
    private String username;
}
